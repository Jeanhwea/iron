package com.avic.mti.iron.measure.service.impl;

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
import com.avic.mti.iron.common.symbol.measure.MeasRoomCateEnum;
import com.avic.mti.iron.measure.domain.entity.MeasRoom;
import com.avic.mti.iron.measure.domain.repo.MeasRoomRepository;
import com.avic.mti.iron.measure.domain.repo.MeasShelfRepository;
import com.avic.mti.iron.measure.helper.MeasRoomHelper;
import com.avic.mti.iron.measure.service.MeasRoomService;
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
public class MeasRoomServiceImpl implements MeasRoomService {

  public static final Logger logger = LoggerFactory.getLogger(MeasRoomServiceImpl.class);

  private final MeasRoomRepository measRoomRepository;
  private final MeasShelfRepository measShelfRepository;

  private final MesConditionBuilder<MeasRoom> mesConditionBuilder;

  @Autowired
  public MeasRoomServiceImpl(
      MeasRoomRepository measRoomRepository,
      MeasShelfRepository measShelfRepository,
      MesConditionBuilder<MeasRoom> mesConditionBuilder) {
    this.measRoomRepository = measRoomRepository;
    this.measShelfRepository = measShelfRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateMeasRoom(MeasRoom measRoom) {
    if (MeasRoomCateEnum.of(measRoom.roomCate()) == MeasRoomCateEnum.Unknown) {
      throw new UnknownEnumException("roomCate", measRoom.roomCate(), MeasRoomCateEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(measRoom.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", measRoom.creatorNC());
    }

    if (measRoom.areaNum() < 0) {
      throw new BadRequestException("库房 {0} 的 areaNum = {1}", measRoom, measRoom.areaNum());
    }

    if (measRoom.shelfNum() < 0) {
      throw new BadRequestException("库房 {0} 的 shelfNum = {1}", measRoom, measRoom.shelfNum());
    }

    if (measRoom.floorNum() < 0) {
      throw new BadRequestException("库房 {0} 的 floorNum = {1}", measRoom, measRoom.floorNum());
    }

    if (measRoom.placeNum() < 0) {
      throw new BadRequestException("库房 {0} 的 placeNum = {1}", measRoom, measRoom.placeNum());
    }

    if (measRoom.gridNum() < 0) {
      throw new BadRequestException("库房 {0} 的 gridNum = {1}", measRoom, measRoom.gridNum());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasRoom> findAllMeasRooms(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasRoom> builder = mesConditionBuilder.init(params, fields);
    return this.measRoomRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public MeasRoom findById(long measRoomId) {
    return this.measRoomRepository
        .fetchById(measRoomId)
        .orElseThrow(() -> new DatumNotExistException("MeasRoom", measRoomId));
  }

  @Override
  public MeasRoom createMeasRoom(Map<String, Object> params) {
    MeasRoom newMeasRoom = MeasRoomHelper.assignMeasRoom(new MeasRoom(), params);
    this.measRoomRepository
        .findByRoomCode(newMeasRoom.roomCode())
        .ifPresent(
            dummy -> {
              throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newMeasRoom.roomCode());
            });
    this.validateMeasRoom(newMeasRoom);
    return this.measRoomRepository.save(newMeasRoom);
  }

  @Override
  public MeasRoom replaceMeasRoom(long measRoomId, Map<String, Object> params) {
    MeasRoom prevMeasRoom = this.findById(measRoomId);
    String newRoomCode = ParamReader.init(params).stringFromKey("roomCode").orElse(null);
    if (!StringHelper.isNonBlankEqual(newRoomCode, prevMeasRoom.roomCode())) {
      this.measRoomRepository
          .findByRoomCode(newRoomCode)
          .ifPresent(
              dummy -> {
                throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newRoomCode);
              });
    }

    MeasRoom currMeasRoom = MeasRoomHelper.assignMeasRoom(prevMeasRoom, params);
    this.validateMeasRoom(currMeasRoom);
    return this.measRoomRepository.save(currMeasRoom);
  }

  @Override
  public void deleteMeasRoom(long measRoomId) {
    MeasRoom measRoom = this.findById(measRoomId);
    int countShelf = this.measShelfRepository.countAllByShelfCodeStartsWith(measRoom.roomCode());
    if (countShelf > 0) {
      throw new BadRequestException("存在 {0} 条库位信息, 不允许删除 {1} 的库房", countShelf, measRoom);
    }

    this.measRoomRepository.deleteById(measRoomId);
  }

  @Override
  public MeasRoom initMeasRoom(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    String roomCode =
        reader
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("缺少 roomCode 参数"));
    List<Long> dimList = MeasRoomHelper.getInitDimension(params);
    logger.info("用户 {} 开始初始化深度为 {} 的库位表数据", updateUser, dimList);

    MeasRoom prevMeasRoom =
        this.measRoomRepository
            .findByRoomCode(roomCode)
            .orElseThrow(() -> new DatumNotExistException("MeasRoom", "roomCode", roomCode));
    MeasRoom currMeasRoom = MeasRoomHelper.assignMeasRoom(prevMeasRoom, params);
    this.validateMeasRoom(currMeasRoom);
    this.measRoomRepository.save(currMeasRoom);

    createChildrenMeasRooms(currMeasRoom, 1, dimList, updateUser);

    return currMeasRoom;
  }

  @Override
  public Map<String, Object> getMeasRoomTree(long measRoomId, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    MeasRoom measRoom = this.findById(measRoomId);
    logger.debug("用户 {} 获取工具库房 {} 树结构", updateUser, measRoom);
    return buildMeasRoomNodeRecursively(measRoom, "children");
  }

  /**
   * 递归生成库房树
   *
   * @author Jinghui Hu
   * @since 2020-11-02, JDK1.8
   */
  private Map<String, Object> buildMeasRoomNodeRecursively(MeasRoom parent, String key) {
    List<Map<String, Object>> childrenList =
        this.measRoomRepository.countAllByParentRoomId(parent.id()) > 0
            ? this.measRoomRepository.findAllByParentRoomIdOrderByRoomCode(parent.id()).stream()
                .map(child -> buildMeasRoomNodeRecursively(child, key))
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
  private void createChildrenMeasRooms(
      MeasRoom parent, int deepness, List<Long> dimList, String updateUser) {
    long childrenCount = dimList.get(deepness - 1);

    for (int i = 0; i < childrenCount; i++) {
      MeasRoom child = new MeasRoom();
      child.updateUser(updateUser);
      child.creatorNC(parent.creatorNC());
      child.createDate(parent.createDate());

      String roomCode = String.format("%s-%02d", parent.roomCode(), i + 1);
      child.roomCode(roomCode);
      child.parentRoomId(parent.id());
      child.roomName(MeasRoomHelper.getRoomName(deepness + 1, i));
      child.roomCate(deepness + 1);

      this.measRoomRepository.save(child);

      if (deepness < dimList.size()) {
        createChildrenMeasRooms(child, deepness + 1, dimList, updateUser);
      }
    }
  }
}
