package com.avic.mti.iron.tool.service.impl;

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
import com.avic.mti.iron.common.symbol.tool.ToolRoomCateEnum;
import com.avic.mti.iron.tool.domain.entity.ToolRoom;
import com.avic.mti.iron.tool.domain.repo.ToolRoomRepository;
import com.avic.mti.iron.tool.domain.repo.ToolShelfRepository;
import com.avic.mti.iron.tool.helper.ToolRoomHelper;
import com.avic.mti.iron.tool.service.ToolRoomService;
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
public class ToolRoomServiceImpl implements ToolRoomService {

  public static final Logger logger = LoggerFactory.getLogger(ToolRoomServiceImpl.class);

  private final ToolRoomRepository toolRoomRepository;
  private final ToolShelfRepository toolShelfRepository;

  private final MesConditionBuilder<ToolRoom> mesConditionBuilder;

  @Autowired
  public ToolRoomServiceImpl(
      ToolRoomRepository toolRoomRepository,
      ToolShelfRepository toolShelfRepository,
      MesConditionBuilder<ToolRoom> mesConditionBuilder) {
    this.toolRoomRepository = toolRoomRepository;
    this.toolShelfRepository = toolShelfRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  private void validateToolRoom(ToolRoom toolRoom) {
    if (ToolRoomCateEnum.of(toolRoom.roomCate()) == ToolRoomCateEnum.Unknown) {
      throw new UnknownEnumException("roomCate", toolRoom.roomCate(), ToolRoomCateEnum.expect());
    }

    if (NameCodeHelper.validateNameCode(toolRoom.creatorNC())) {
      throw new WrongParameterFormatException("创建人", "名称[编码]", toolRoom.creatorNC());
    }

    if (toolRoom.areaNum() < 0) {
      throw new BadRequestException("库房 {0} 的 areaNum = {1}", toolRoom, toolRoom.areaNum());
    }

    if (toolRoom.shelfNum() < 0) {
      throw new BadRequestException("库房 {0} 的 shelfNum = {1}", toolRoom, toolRoom.shelfNum());
    }

    if (toolRoom.floorNum() < 0) {
      throw new BadRequestException("库房 {0} 的 floorNum = {1}", toolRoom, toolRoom.floorNum());
    }

    if (toolRoom.placeNum() < 0) {
      throw new BadRequestException("库房 {0} 的 placeNum = {1}", toolRoom, toolRoom.placeNum());
    }

    if (toolRoom.gridNum() < 0) {
      throw new BadRequestException("库房 {0} 的 gridNum = {1}", toolRoom, toolRoom.gridNum());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolRoom> findAllToolRooms(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolRoom> builder = mesConditionBuilder.init(params, fields);
    return this.toolRoomRepository.findAll(builder.spec());
  }

  @Override
  @Transactional(readOnly = true)
  public ToolRoom findById(long toolRoomId) {
    return this.toolRoomRepository
        .fetchById(toolRoomId)
        .orElseThrow(() -> new DatumNotExistException("ToolRoom", toolRoomId));
  }

  @Override
  public ToolRoom createToolRoom(Map<String, Object> params) {
    ToolRoom newToolRoom = ToolRoomHelper.assignToolRoom(new ToolRoom(), params);
    this.toolRoomRepository
        .findByRoomCode(newToolRoom.roomCode())
        .ifPresent(
            dummy -> {
              throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newToolRoom.roomCode());
            });
    this.validateToolRoom(newToolRoom);
    return this.toolRoomRepository.save(newToolRoom);
  }

  @Override
  public ToolRoom replaceToolRoom(long toolRoomId, Map<String, Object> params) {
    ToolRoom prevToolRoom = this.findById(toolRoomId);
    String newRoomCode = ParamReader.init(params).stringFromKey("roomCode").orElse(null);
    if (!StringHelper.isNonBlankEqual(newRoomCode, prevToolRoom.roomCode())) {
      this.toolRoomRepository
          .findByRoomCode(newRoomCode)
          .ifPresent(
              dummy -> {
                throw new BadRequestException("库房编码 {0} 已经存在, 不允许重复创建", newRoomCode);
              });
    }

    ToolRoom currToolRoom = ToolRoomHelper.assignToolRoom(prevToolRoom, params);
    this.validateToolRoom(currToolRoom);
    return this.toolRoomRepository.save(currToolRoom);
  }

  @Override
  public void deleteToolRoom(long toolRoomId) {
    ToolRoom toolRoom = this.findById(toolRoomId);
    int countShelf = this.toolShelfRepository.countAllByShelfCodeStartsWith(toolRoom.roomCode());
    if (countShelf > 0) {
      throw new BadRequestException("存在 {0} 条库位信息, 不允许删除 {1} 的库房", countShelf, toolRoom);
    }

    this.toolRoomRepository.deleteById(toolRoomId);
  }

  @Override
  public ToolRoom initToolRoom(Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    String roomCode =
        reader
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("缺少 roomCode 参数"));
    List<Long> dimList = ToolRoomHelper.getInitDimension(params);
    logger.info("用户 {} 开始初始化深度为 {} 的库位表数据", updateUser, dimList);

    ToolRoom prevToolRoom =
        this.toolRoomRepository
            .findByRoomCode(roomCode)
            .orElseThrow(() -> new DatumNotExistException("ToolRoom", "roomCode", roomCode));
    ToolRoom currToolRoom = ToolRoomHelper.assignToolRoom(prevToolRoom, params);
    this.validateToolRoom(currToolRoom);
    this.toolRoomRepository.save(currToolRoom);

    createChildrenToolRooms(currToolRoom, 1, dimList, updateUser);

    return currToolRoom;
  }

  @Override
  public Map<String, Object> getToolRoomTree(long toolRoomId, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    String updateUser =
        reader
            .stringFromKey("updateUser")
            .orElseThrow(() -> new BadRequestException("缺少更新用户 updateUser 参数"));
    ToolRoom toolRoom = this.findById(toolRoomId);
    logger.debug("用户 {} 获取工具库房 {} 树结构", updateUser, toolRoom);
    return buildToolRoomNodeRecursively(toolRoom, "children");
  }

  /**
   * 递归生成库房树
   *
   * @author Jinghui Hu
   * @since 2020-11-02, JDK1.8
   */
  private Map<String, Object> buildToolRoomNodeRecursively(ToolRoom parent, String key) {
    List<Map<String, Object>> childrenList =
        this.toolRoomRepository.countAllByParentRoomId(parent.id()) > 0
            ? this.toolRoomRepository.findAllByParentRoomIdOrderByRoomCode(parent.id()).stream()
                .map(child -> buildToolRoomNodeRecursively(child, key))
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
  private void createChildrenToolRooms(
      ToolRoom parent, int deepness, List<Long> dimList, String updateUser) {
    long childrenCount = dimList.get(deepness - 1);

    for (int i = 0; i < childrenCount; i++) {
      ToolRoom child = new ToolRoom();
      child.updateUser(updateUser);
      child.creatorNC(parent.creatorNC());
      child.createDate(parent.createDate());

      String roomCode = String.format("%s-%02d", parent.roomCode(), i + 1);
      child.roomCode(roomCode);
      child.parentRoomId(parent.id());
      child.roomName(ToolRoomHelper.getRoomName(deepness + 1, i));
      child.roomCate(deepness + 1);

      this.toolRoomRepository.save(child);

      if (deepness < dimList.size()) {
        createChildrenToolRooms(child, deepness + 1, dimList, updateUser);
      }
    }
  }
}
