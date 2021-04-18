package com.avic.mti.iron.tool.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import com.avic.mti.iron.tool.domain.entity.ToolShelf;
import com.avic.mti.iron.tool.domain.repo.ToolInRepository;
import com.avic.mti.iron.tool.domain.repo.ToolShelfRepository;
import com.avic.mti.iron.tool.helper.ToolInHelper;
import com.avic.mti.iron.tool.helper.ToolShelfHelper;
import com.avic.mti.iron.tool.service.ToolShelfService;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ToolShelfServiceImpl implements ToolShelfService {

  public static final Logger logger = LoggerFactory.getLogger(ToolShelfServiceImpl.class);

  private final ToolShelfRepository toolShelfRepository;

  private final ToolInRepository toolInRepository;

  private final MesConditionBuilder<ToolShelf> mesConditionBuilder;

  @Autowired
  public ToolShelfServiceImpl(
      ToolShelfRepository toolShelfRepository,
      ToolInRepository toolInRepository,
      MesConditionBuilder<ToolShelf> mesConditionBuilder) {
    this.toolShelfRepository = toolShelfRepository;
    this.toolInRepository = toolInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ToolShelf> findAllToolShelfs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<ToolShelf> builder = mesConditionBuilder.init(params, fields);
    return this.toolShelfRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public ToolShelf findById(long toolShelfId) {
    return this.toolShelfRepository
        .fetchById(toolShelfId)
        .orElseThrow(() -> new DatumNotExistException("ToolShelf", toolShelfId));
  }

  @Override
  public ToolShelf createToolShelf(Map<String, Object> params) {
    ToolShelf newToolShelf = ToolShelfHelper.assignToolShelf(new ToolShelf(), params);
    return this.toolShelfRepository.save(newToolShelf);
  }

  @Override
  public ToolShelf replaceToolShelf(long toolShelfId, Map<String, Object> params) {
    ToolShelf prevToolShelf = this.findById(toolShelfId);
    ToolShelf currToolShelf = ToolShelfHelper.assignToolShelf(prevToolShelf, params);
    return this.toolShelfRepository.save(currToolShelf);
  }

  @Override
  public void deleteToolShelf(long toolShelfId) {
    this.toolShelfRepository.deleteById(toolShelfId);
  }

  @Override
  public List<ToolShelf> enterShelfList(ToolIn toolIn) {
    List<Map<String, Object>> shelfJson =
        JsonHelper.parseList(toolIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("传入的 shelfJson 字段无法解析"));
    List<ToolShelf> shelfList = new LinkedList<>();
    for (Map<String, Object> shelfParam : shelfJson) {
      ParamReader shelfReader = ParamReader.init(shelfParam);

      String shelfCode =
          shelfReader
              .stringFromKey("shelfCode")
              .orElseThrow(() -> new BadRequestException("入库参数中缺少 shelfCode 字段"));
      long storeNum =
          shelfReader
              .longFromKey("storeNum")
              .orElseThrow(() -> new BadRequestException("入库的参数中缺少 storeNum 字段"));

      ToolShelf shelf =
          this.toolShelfRepository
              .findByShelfCode(shelfCode)
              .orElseGet(
                  () -> {
                    ToolShelf toolShelf =
                        ToolShelfHelper.assignToolShelf(new ToolShelf(), shelfParam);
                    toolShelf.creatorNC(toolIn.inNC());
                    toolShelf.createDate(toolIn.inDate());
                    return toolShelf;
                  });

      ToolShelf toolShelf =
          ToolShelfHelper.appendStockIn(toolIn.updateUser(), shelf, toolIn, storeNum);
      this.toolShelfRepository.save(toolShelf);
      shelfList.add(toolShelf);
    }

    return shelfList;
  }

  @Override
  public List<ToolShelf> exitShelfList(ToolOut toolOut) {
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(toolOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("工具出库 {0} 缺少 shelfJson 参数", toolOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(ToolInHelper::extractShelfCode));

    Set<String> shelfCodes = outShelfMap.keySet();

    List<ToolShelf> shelfList = this.toolShelfRepository.findAllByShelfCodeIn(shelfCodes);
    if (shelfList.isEmpty()) {
      throw new BadRequestException("获取到的库位信息为空");
    }

    logger.debug("工具库位表中修改出库表，获取到 {} 条库位: {}", shelfList.size(), shelfList);
    for (ToolShelf shelf : shelfList) {
      List<Map<String, Object>> outs = outShelfMap.get(shelf.shelfCode());
      if (outs == null || outs.isEmpty()) {
        throw new BadRequestException("库位 {0} 不存在，无法出库", shelf.shelfCode());
      } else if (outs.size() > 1) {
        throw new BadRequestException("库位 {0} 存在重复数据项，无法出库", shelf.shelfCode());
      }

      Map<String, Object> out = outs.get(0);
      long deltaStockNum =
          ParamReader.init(out)
              .longFromKey("storeNum")
              .orElseThrow(() -> new BadRequestException("JSON 对象中缺少 storeNum 键"));

      ToolShelfHelper.removeStockIn(toolOut.updateUser(), shelf, toolOut.inId(), deltaStockNum);
    }

    return this.toolShelfRepository.saveAll(shelfList);
  }

  @Override
  public List<ToolIn> findShelfIns(Map<String, Object> params) {
    String roomCode =
        ParamReader.init(params)
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("无法获取 roomCode 参数"));

    List<ToolShelf> shelfs = this.toolShelfRepository.findAllByRoomCodeStartsWith(roomCode);
    List<Long> inIds =
        shelfs.stream().flatMap(ToolInHelper::extractInIds).distinct().collect(Collectors.toList());

    logger.debug("从 {} 条库位中获取到 {} 条工具入库列表", shelfs.size(), inIds.size());
    return this.toolInRepository.findByIdIn(inIds);
  }
}
