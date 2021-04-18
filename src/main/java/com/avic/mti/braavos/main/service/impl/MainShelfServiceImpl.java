package com.avic.mti.iron.main.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.domain.entity.MainShelf;
import com.avic.mti.iron.main.domain.repo.MainInRepository;
import com.avic.mti.iron.main.domain.repo.MainShelfRepository;
import com.avic.mti.iron.main.helper.MainInHelper;
import com.avic.mti.iron.main.helper.MainShelfHelper;
import com.avic.mti.iron.main.service.MainShelfService;
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
public class MainShelfServiceImpl implements MainShelfService {

  public static final Logger logger = LoggerFactory.getLogger(MainShelfServiceImpl.class);

  private final MainShelfRepository mainShelfRepository;

  private final MainInRepository mainInRepository;

  private final MesConditionBuilder<MainShelf> mesConditionBuilder;

  @Autowired
  public MainShelfServiceImpl(
      MainShelfRepository mainShelfRepository,
      MainInRepository mainInRepository,
      MesConditionBuilder<MainShelf> mesConditionBuilder) {
    this.mainShelfRepository = mainShelfRepository;
    this.mainInRepository = mainInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public List<MainShelf> findAllMainShelfs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<MainShelf> builder = mesConditionBuilder.init(params, fields);
    return this.mainShelfRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public MainShelf findById(long mainShelfId) {
    return this.mainShelfRepository
        .fetchById(mainShelfId)
        .orElseThrow(() -> new DatumNotExistException("MainShelf", mainShelfId));
  }

  @Override
  public MainShelf createMainShelf(Map<String, Object> params) {
    MainShelf newMainShelf = MainShelfHelper.assignMainShelf(new MainShelf(), params);
    return this.mainShelfRepository.save(newMainShelf);
  }

  @Override
  public MainShelf replaceMainShelf(long mainShelfId, Map<String, Object> params) {
    MainShelf prevMainShelf = this.findById(mainShelfId);
    MainShelf currMainShelf = MainShelfHelper.assignMainShelf(prevMainShelf, params);
    return this.mainShelfRepository.save(currMainShelf);
  }

  @Override
  public void deleteMainShelf(long mainShelfId) {
    this.mainShelfRepository.deleteById(mainShelfId);
  }

  @Override
  public List<MainShelf> enterShelfList(MainIn mainIn) {
    List<Map<String, Object>> shelfJson =
        JsonHelper.parseList(mainIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("传入的 shelfJson 字段无法解析"));
    List<MainShelf> shelfList = new LinkedList<>();
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

      MainShelf shelf =
          this.mainShelfRepository
              .findByShelfCode(shelfCode)
              .orElseGet(
                  () -> {
                    MainShelf mainShelf =
                        MainShelfHelper.assignMainShelf(new MainShelf(), shelfParam);
                    mainShelf.creatorNC(mainIn.inNC());
                    mainShelf.createDate(mainIn.inDate());
                    return mainShelf;
                  });

      MainShelf mainShelf =
          MainShelfHelper.appendStockIn(mainIn.updateUser(), shelf, mainIn, storeNum);

      mainShelf.measure(mainIn.measure());
      this.mainShelfRepository.save(mainShelf);
      shelfList.add(mainShelf);
    }

    return shelfList;
  }

  @Override
  public List<MainShelf> exitShelfList(MainOut mainOut) {
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(mainOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("主材出库 {0} 缺少 shelfJson 参数", mainOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(MainInHelper::extractShelfCode));

    Set<String> shelfCodes = outShelfMap.keySet();

    List<MainShelf> shelfList = this.mainShelfRepository.findAllByShelfCodeIn(shelfCodes);
    if (shelfList.isEmpty()) {
      throw new BadRequestException("获取到的库位信息为空");
    }

    logger.debug("主材库位表中修改出库表，获取到 {} 条库位: {}", shelfList.size(), shelfList);
    for (MainShelf shelf : shelfList) {
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

      MainShelfHelper.removeStockIn(mainOut.updateUser(), shelf, mainOut.inId(), deltaStockNum);
    }

    return this.mainShelfRepository.saveAll(shelfList);
  }

  @Override
  public List<MainIn> findShelfIns(Map<String, Object> params) {
    String roomCode =
        ParamReader.init(params)
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("无法获取 roomCode 参数"));

    List<MainShelf> shelfs = this.mainShelfRepository.findAllByRoomCodeStartsWith(roomCode);
    List<Long> inIds =
        shelfs.stream().flatMap(MainInHelper::extractInIds).distinct().collect(Collectors.toList());

    logger.debug("从 {} 条库位中获取到 {} 条主材入库列表", shelfs.size(), inIds.size());
    return this.mainInRepository.findByIdIn(inIds);
  }
}
