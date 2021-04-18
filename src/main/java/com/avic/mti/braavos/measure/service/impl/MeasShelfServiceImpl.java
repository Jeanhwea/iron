package com.avic.mti.iron.measure.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import com.avic.mti.iron.measure.domain.entity.MeasShelf;
import com.avic.mti.iron.measure.domain.repo.MeasInRepository;
import com.avic.mti.iron.measure.domain.repo.MeasShelfRepository;
import com.avic.mti.iron.measure.helper.MeasInHelper;
import com.avic.mti.iron.measure.helper.MeasShelfHelper;
import com.avic.mti.iron.measure.service.MeasShelfService;
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
public class MeasShelfServiceImpl implements MeasShelfService {

  public static final Logger logger = LoggerFactory.getLogger(MeasShelfServiceImpl.class);

  private final MeasShelfRepository measShelfRepository;

  private final MeasInRepository measInRepository;

  private final MesConditionBuilder<MeasShelf> mesConditionBuilder;

  @Autowired
  public MeasShelfServiceImpl(
      MeasShelfRepository measShelfRepository,
      MeasInRepository measInRepository,
      MesConditionBuilder<MeasShelf> mesConditionBuilder) {
    this.measShelfRepository = measShelfRepository;
    this.measInRepository = measInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public List<MeasShelf> findAllMeasShelfs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MeasShelf> builder = mesConditionBuilder.init(params, fields);
    return this.measShelfRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public MeasShelf findById(long measShelfId) {
    return this.measShelfRepository
        .fetchById(measShelfId)
        .orElseThrow(() -> new DatumNotExistException("MeasShelf", measShelfId));
  }

  @Override
  public MeasShelf createMeasShelf(Map<String, Object> params) {
    MeasShelf newMeasShelf = MeasShelfHelper.assignMeasShelf(new MeasShelf(), params);
    return this.measShelfRepository.save(newMeasShelf);
  }

  @Override
  public MeasShelf replaceMeasShelf(long measShelfId, Map<String, Object> params) {
    MeasShelf prevMeasShelf = this.findById(measShelfId);
    MeasShelf currMeasShelf = MeasShelfHelper.assignMeasShelf(prevMeasShelf, params);
    return this.measShelfRepository.save(currMeasShelf);
  }

  @Override
  public void deleteMeasShelf(long measShelfId) {
    this.measShelfRepository.deleteById(measShelfId);
  }

  @Override
  public List<MeasShelf> enterShelfList(MeasIn measIn) {
    List<Map<String, Object>> shelfJson =
        JsonHelper.parseList(measIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("传入的 shelfJson 字段无法解析"));
    List<MeasShelf> shelfList = new LinkedList<>();
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

      MeasShelf shelf =
          this.measShelfRepository
              .findByShelfCode(shelfCode)
              .orElseGet(
                  () -> {
                    MeasShelf measShelf =
                        MeasShelfHelper.assignMeasShelf(new MeasShelf(), shelfParam);
                    measShelf.creatorNC(measIn.inNC());
                    measShelf.createDate(measIn.inDate());
                    return measShelf;
                  });

      MeasShelf measShelf =
          MeasShelfHelper.appendStockIn(measIn.updateUser(), shelf, measIn, storeNum);
      this.measShelfRepository.save(measShelf);
      shelfList.add(measShelf);
    }

    return shelfList;
  }

  @Override
  public List<MeasShelf> exitShelfList(MeasOut measOut) {
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(measOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("工具出库 {0} 缺少 shelfJson 参数", measOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(MeasInHelper::extractShelfCode));

    Set<String> shelfCodes = outShelfMap.keySet();

    List<MeasShelf> shelfList = this.measShelfRepository.findAllByShelfCodeIn(shelfCodes);
    if (shelfList.isEmpty()) {
      throw new BadRequestException("获取到的库位信息为空");
    }

    logger.debug("工具库位表中修改出库表，获取到 {} 条库位: {}", shelfList.size(), shelfList);
    for (MeasShelf shelf : shelfList) {
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

      MeasShelfHelper.removeStockIn(measOut.updateUser(), shelf, measOut.inId(), deltaStockNum);
    }

    return this.measShelfRepository.saveAll(shelfList);
  }

  @Override
  public List<MeasIn> findShelfIns(Map<String, Object> params) {
    String roomCode =
        ParamReader.init(params)
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("无法获取 roomCode 参数"));

    List<MeasShelf> shelfs = this.measShelfRepository.findAllByRoomCodeStartsWith(roomCode);
    List<Long> inIds =
        shelfs.stream().flatMap(MeasInHelper::extractInIds).distinct().collect(Collectors.toList());

    logger.debug("从 {} 条库位中获取到 {} 条工具入库列表", shelfs.size(), inIds.size());
    return this.measInRepository.findByIdIn(inIds);
  }
}
