package com.avic.mti.iron.waste.service.impl;

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
import com.avic.mti.iron.common.symbol.waste.WprdRoomCateEnum;
import com.avic.mti.iron.waste.domain.entity.WprdRoom;
import com.avic.mti.iron.waste.domain.repo.WprdRoomRepository;
import com.avic.mti.iron.waste.domain.repo.WprdShelfRepository;
import com.avic.mti.iron.waste.helper.WprdRoomHelper;
import com.avic.mti.iron.waste.service.WprdRoomService;
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
public class WprdRoomServiceImpl implements WprdRoomService {

  public static final Logger logger = LoggerFactory.getLogger(WprdRoomServiceImpl.class);

  private final WprdRoomRepository wprdRoomRepository;
  private final WprdShelfRepository wprdShelfRepository;

  private final MesConditionBuilder<WprdRoom> mesConditionBuilder;

  @Autowired
  public WprdRoomServiceImpl(
      WprdRoomRepository wprdRoomRepository,
      WprdShelfRepository wprdShelfRepository,
      MesConditionBuilder<WprdRoom> mesConditionBuilder) {
    this.wprdRoomRepository = wprdRoomRepository;
    this.wprdShelfRepository = wprdShelfRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateWprdRoom(WprdRoom wprdRoom) {
    if (WprdRoomCateEnum.of(wprdRoom.roomCate()) == WprdRoomCateEnum.Unknown) {
      throw new UnknownEnumException("roomCate", wprdRoom.roomCate(), WprdRoomCateEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(wprdRoom.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", wprdRoom.creatorNC());
    }

    if (wprdRoom.areaNum() < 0) {
      throw new BadRequestException("库房 {0} 的 areaNum = {1}", wprdRoom, wprdRoom.areaNum());
    }

    if (wprdRoom.shelfNum() < 0) {
      throw new BadRequestException("库房 {0} 的 shelfNum = {1}", wprdRoom, wprdRoom.shelfNum());
    }

    if (wprdRoom.floorNum() < 0) {
      throw new BadRequestException("库房 {0} 的 floorNum = {1}", wprdRoom, wprdRoom.floorNum());
    }

    if (wprdRoom.placeNum() < 0) {
      throw new BadRequestException("库房 {0} 的 placeNum = {1}", wprdRoom, wprdRoom.placeNum());
    }

    if (wprdRoom.gridNum() < 0) {
      throw new BadRequestException("库房 {0} 的 gridNum = {1}", wprdRoom, wprdRoom.gridNum());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdRoom> findAllWprdRooms(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdRoom> builder = mesConditionBuilder.init(params, fields);
    return this.wprdRoomRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public WprdRoom findById(long wprdRoomId) {
    return this.wprdRoomRepository
        .fetchById(wprdRoomId)
        .orElseThrow(() -> new DatumNotExistException("WprdRoom", wprdRoomId));
  }

  @Override
  public WprdRoom createWprdRoom(Map<String, Object> params) {
    WprdRoom newWprdRoom = WprdRoomHelper.assignWprdRoom(new WprdRoom(), params);
    this.wprdRoomRepository
        .findByRoomCode(newWprdRoom.roomCode())
        .ifPresent(
            dummy -> {
              throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newWprdRoom.roomCode());
            });
    this.validateWprdRoom(newWprdRoom);
    return this.wprdRoomRepository.save(newWprdRoom);
  }

  @Override
  public WprdRoom replaceWprdRoom(long wprdRoomId, Map<String, Object> params) {
    WprdRoom prevWprdRoom = this.findById(wprdRoomId);
    String newRoomCode = ParamReader.init(params).stringFromKey("roomCode").orElse(null);
    if (!StringHelper.isNonBlankEqual(newRoomCode, prevWprdRoom.roomCode())) {
      this.wprdRoomRepository
          .findByRoomCode(newRoomCode)
          .ifPresent(
              dummy -> {
                throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newRoomCode);
              });
    }

    WprdRoom currWprdRoom = WprdRoomHelper.assignWprdRoom(prevWprdRoom, params);
    this.validateWprdRoom(currWprdRoom);
    return this.wprdRoomRepository.save(currWprdRoom);
  }

  @Override
  public void deleteWprdRoom(long wprdRoomId) {
    WprdRoom wprdRoom = this.findById(wprdRoomId);
    int countShelf = this.wprdShelfRepository.countAllByShelfCodeStartsWith(wprdRoom.roomCode());
    if (countShelf > 0) {
      throw new BadRequestException("存在 {0} 条库位信息, 不允许删除 {1} 的库房", countShelf, wprdRoom);
    }

    this.wprdRoomRepository.deleteById(wprdRoomId);
  }

  @Override
  public WprdRoom initWprdRoom(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    String roomCode =
        reader
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("缺少 roomCode 参数"));
    List<Long> dimList = WprdRoomHelper.getInitDimension(params);
    logger.info("用户 {} 开始初始化深度为 {} 的库位表数据", updateUser, dimList);

    WprdRoom prevWprdRoom =
        this.wprdRoomRepository
            .findByRoomCode(roomCode)
            .orElseThrow(() -> new DatumNotExistException("WprdRoom", "roomCode", roomCode));
    WprdRoom currWprdRoom = WprdRoomHelper.assignWprdRoom(prevWprdRoom, params);
    this.validateWprdRoom(currWprdRoom);
    this.wprdRoomRepository.save(currWprdRoom);

    createChildrenWprdRooms(currWprdRoom, 1, dimList, updateUser);

    return currWprdRoom;
  }

  @Override
  public Map<String, Object> getWprdRoomTree(long wprdRoomId, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    WprdRoom wprdRoom = this.findById(wprdRoomId);
    logger.debug("用户 {} 获取废品库房 {} 树结构", updateUser, wprdRoom);
    return buildWprdRoomNodeRecursively(wprdRoom, "children");
  }

  /**
   * 递归生成库房树
   *
   * @author Jinghui Hu
   * @since 2020-11-02, JDK1.8
   */
  private Map<String, Object> buildWprdRoomNodeRecursively(WprdRoom parent, String key) {
    List<Map<String, Object>> childrenList =
        this.wprdRoomRepository.countAllByParentRoomId(parent.id()) > 0
            ? this.wprdRoomRepository.findAllByParentRoomIdOrderByRoomCode(parent.id()).stream()
                .map(child -> buildWprdRoomNodeRecursively(child, key))
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
  private void createChildrenWprdRooms(
      WprdRoom parent, int deepness, List<Long> dimList, String updateUser) {
    long childrenCount = dimList.get(deepness - 1);

    for (int i = 0; i < childrenCount; i++) {
      WprdRoom child = new WprdRoom();
      child.updateUser(updateUser);
      child.creatorNC(parent.creatorNC());
      child.createDate(parent.createDate());

      String roomCode = String.format("%s-%02d", parent.roomCode(), i + 1);
      child.roomCode(roomCode);
      child.parentRoomId(parent.id());
      child.roomName(WprdRoomHelper.getRoomName(deepness + 1, i));
      child.roomCate(deepness + 1);

      this.wprdRoomRepository.save(child);

      if (deepness < dimList.size()) {
        createChildrenWprdRooms(child, deepness + 1, dimList, updateUser);
      }
    }
  }
}
