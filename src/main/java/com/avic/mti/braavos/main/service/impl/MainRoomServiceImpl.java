package com.avic.mti.iron.main.service.impl;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.exception.UnknownEnumException;
import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.NameCodeHelper;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.main.MainRoomCateEnum;
import com.avic.mti.iron.main.domain.entity.MainRoom;
import com.avic.mti.iron.main.domain.repo.MainRoomRepository;
import com.avic.mti.iron.main.domain.repo.MainShelfRepository;
import com.avic.mti.iron.main.helper.MainRoomHelper;
import com.avic.mti.iron.main.service.MainRoomService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MainRoomServiceImpl implements MainRoomService {

  public static final Logger logger = LoggerFactory.getLogger(MainRoomServiceImpl.class);

  private final MainRoomRepository mainRoomRepository;
  private final MainShelfRepository mainShelfRepository;

  private final MesConditionBuilder<MainRoom> mesConditionBuilder;

  @Autowired
  public MainRoomServiceImpl(
      MainRoomRepository mainRoomRepository,
      MainShelfRepository mainShelfRepository,
      MesConditionBuilder<MainRoom> mesConditionBuilder) {
    this.mainRoomRepository = mainRoomRepository;
    this.mainShelfRepository = mainShelfRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMainRoom(MainRoom mainRoom) {
    if (MainRoomCateEnum.of(mainRoom.roomCate()) == MainRoomCateEnum.Unknown) {
      throw new UnknownEnumException("roomCate", mainRoom.roomCate(), MainRoomCateEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(mainRoom.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", mainRoom.creatorNC());
    }

    if (mainRoom.areaNum() < 0) {
      throw new BadRequestException("库房 {0} 的 areaNum = {1}", mainRoom, mainRoom.areaNum());
    }

    if (mainRoom.shelfNum() < 0) {
      throw new BadRequestException("库房 {0} 的 shelfNum = {1}", mainRoom, mainRoom.shelfNum());
    }

    if (mainRoom.floorNum() < 0) {
      throw new BadRequestException("库房 {0} 的 floorNum = {1}", mainRoom, mainRoom.floorNum());
    }

    if (mainRoom.placeNum() < 0) {
      throw new BadRequestException("库房 {0} 的 placeNum = {1}", mainRoom, mainRoom.placeNum());
    }

    if (mainRoom.gridNum() < 0) {
      throw new BadRequestException("库房 {0} 的 gridNum = {1}", mainRoom, mainRoom.gridNum());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainRoom> findAllMainRooms(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainRoom> builder = mesConditionBuilder.init(params, fields);
    return this.mainRoomRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MainRoom findById(long mainRoomId) {
    return this.mainRoomRepository
        .fetchById(mainRoomId)
        .orElseThrow(() -> new DatumNotExistException("MainRoom", mainRoomId));
  }

  @Override
  public MainRoom createMainRoom(Map<String, Object> params) {
    MainRoom newMainRoom = MainRoomHelper.assignMainRoom(new MainRoom(), params);
    this.mainRoomRepository
        .findByRoomCode(newMainRoom.roomCode())
        .ifPresent(
            dummy -> {
              throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newMainRoom.roomCode());
            });
    this.validateMainRoom(newMainRoom);
    return this.mainRoomRepository.save(newMainRoom);
  }

  @Override
  public MainRoom replaceMainRoom(long mainRoomId, Map<String, Object> params) {
    MainRoom prevMainRoom = this.findById(mainRoomId);
    String newRoomCode = ParamReader.init(params).stringFromKey("roomCode").orElse(null);
    if (!StringHelper.isNonBlankEqual(newRoomCode, prevMainRoom.roomCode())) {
      this.mainRoomRepository
          .findByRoomCode(newRoomCode)
          .ifPresent(
              dummy -> {
                throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newRoomCode);
              });
    }

    MainRoom currMainRoom = MainRoomHelper.assignMainRoom(prevMainRoom, params);
    this.validateMainRoom(currMainRoom);
    return this.mainRoomRepository.save(currMainRoom);
  }

  @Override
  public void deleteMainRoom(long mainRoomId) {
    MainRoom mainRoom = this.findById(mainRoomId);
    int countShelf = this.mainShelfRepository.countAllByShelfCodeStartsWith(mainRoom.roomCode());
    if (countShelf > 0) {
      throw new BadRequestException("存在 {0} 条库位信息, 不允许删除 {1} 的库房", countShelf, mainRoom);
    }

    this.mainRoomRepository.deleteById(mainRoomId);
  }

  @Override
  public MainRoom initMainRoom(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    String roomCode =
        reader
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("缺少 roomCode 参数"));
    List<Long> dimList = MainRoomHelper.getInitDimension(params);
    logger.info("用户 {} 开始初始化深度为 {} 的库位表数据", updateUser, dimList);

    MainRoom prevMainRoom =
        this.mainRoomRepository
            .findByRoomCode(roomCode)
            .orElseThrow(() -> new DatumNotExistException("MainRoom", "roomCode", roomCode));
    MainRoom currMainRoom = MainRoomHelper.assignMainRoom(prevMainRoom, params);
    this.validateMainRoom(currMainRoom);
    this.mainRoomRepository.save(currMainRoom);

    createChildrenMainRooms(currMainRoom, 1, dimList, updateUser);

    return currMainRoom;
  }

  @Override
  public Map<String, Object> getMainRoomTree(long mainRoomId, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    MainRoom mainRoom = this.findById(mainRoomId);
    logger.debug("用户 {} 获取主材库房 {} 树结构", updateUser, mainRoom);
    return buildMainRoomNodeRecursively(mainRoom, "children");
  }

  /**
   * 递归生成库房树
   *
   * @author Jinghui Hu
   * @since 2020-11-02, JDK1.8
   */
  private Map<String, Object> buildMainRoomNodeRecursively(MainRoom parent, String key) {
    List<Map<String, Object>> childrenList =
        this.mainRoomRepository.countAllByParentRoomId(parent.id()) > 0
            ? this.mainRoomRepository.findAllByParentRoomIdOrderByRoomCode(parent.id()).stream()
                .map(child -> buildMainRoomNodeRecursively(child, key))
                .collect(Collectors.toList())
            : null;
    Map<String, Object> result =
        ParamBuilder.init()
            .put("roomId", parent.id())
            .put("roomCode", parent.roomCode())
            .put("roomName", parent.roomName())
            .put("roomCate", parent.roomCate())
            .put(key, childrenList)
            .params();
    return result;
  }

  /**
   * 创建库房数据项
   *
   * @author Jinghui Hu
   * @since 2020-11-02, JDK1.8
   */
  private void createChildrenMainRooms(
      MainRoom parent, int deepness, List<Long> dimList, String updateUser) {
    long childrenCount = dimList.get(deepness - 1);

    for (int i = 0; i < childrenCount; i++) {
      MainRoom child = new MainRoom();
      child.updateUser(updateUser);
      child.creatorNC(parent.creatorNC());
      child.createDate(parent.createDate());

      String roomCode = String.format("%s-%02d", parent.roomCode(), i + 1);
      child.roomCode(roomCode);
      child.parentRoomId(parent.id());
      child.roomName(MainRoomHelper.getRoomName(deepness + 1, i));
      child.roomCate(deepness + 1);

      this.mainRoomRepository.save(child);

      if (deepness < dimList.size()) {
        createChildrenMainRooms(child, deepness + 1, dimList, updateUser);
      }
    }
  }
}
