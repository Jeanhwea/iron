package com.avic.mti.iron.waste.service.impl;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.domain.query.MesConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DatumNotExistException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import com.avic.mti.iron.waste.domain.entity.WprdShelf;
import com.avic.mti.iron.waste.domain.repo.WprdInRepository;
import com.avic.mti.iron.waste.domain.repo.WprdShelfRepository;
import com.avic.mti.iron.waste.helper.WprdInHelper;
import com.avic.mti.iron.waste.helper.WprdShelfHelper;
import com.avic.mti.iron.waste.service.WprdShelfService;
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
public class WprdShelfServiceImpl implements WprdShelfService {

  public static final Logger logger = LoggerFactory.getLogger(WprdShelfServiceImpl.class);

  private final WprdShelfRepository wprdShelfRepository;

  private final WprdInRepository wprdInRepository;

  private final MesConditionBuilder<WprdShelf> mesConditionBuilder;

  @Autowired
  public WprdShelfServiceImpl(
      WprdShelfRepository wprdShelfRepository,
      WprdInRepository wprdInRepository,
      MesConditionBuilder<WprdShelf> mesConditionBuilder) {
    this.wprdShelfRepository = wprdShelfRepository;
    this.wprdInRepository = wprdInRepository;
    this.mesConditionBuilder = mesConditionBuilder;
  }

  @Override
  @Transactional(readOnly = true)
  public List<WprdShelf> findAllWprdShelfs(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    MesConditionBuilder<WprdShelf> builder = mesConditionBuilder.init(params, fields);
    return this.wprdShelfRepository.findAll(builder.spec(), builder.page());
  }

  @Override
  @Transactional(readOnly = true)
  public WprdShelf findById(long wprdShelfId) {
    return this.wprdShelfRepository
        .fetchById(wprdShelfId)
        .orElseThrow(() -> new DatumNotExistException("WprdShelf", wprdShelfId));
  }

  @Override
  public WprdShelf createWprdShelf(Map<String, Object> params) {
    WprdShelf newWprdShelf = WprdShelfHelper.assignWprdShelf(new WprdShelf(), params);
    return this.wprdShelfRepository.save(newWprdShelf);
  }

  @Override
  public WprdShelf replaceWprdShelf(long wprdShelfId, Map<String, Object> params) {
    WprdShelf prevWprdShelf = this.findById(wprdShelfId);
    WprdShelf currWprdShelf = WprdShelfHelper.assignWprdShelf(prevWprdShelf, params);
    return this.wprdShelfRepository.save(currWprdShelf);
  }

  @Override
  public void deleteWprdShelf(long wprdShelfId) {
    this.wprdShelfRepository.deleteById(wprdShelfId);
  }

  @Override
  public List<WprdShelf> enterShelfList(WprdIn wprdIn) {
    List<Map<String, Object>> shelfJson =
        JsonHelper.parseList(wprdIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("传入的 shelfJson 字段无法解析"));
    List<WprdShelf> shelfList = new LinkedList<>();
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

      WprdShelf shelf =
          this.wprdShelfRepository
              .findByShelfCode(shelfCode)
              .orElseGet(
                  () -> {
                    WprdShelf wprdShelf =
                        WprdShelfHelper.assignWprdShelf(new WprdShelf(), shelfParam);
                    wprdShelf.creatorNC(wprdIn.inNC());
                    wprdShelf.createDate(wprdIn.inDate());
                    return wprdShelf;
                  });

      WprdShelf wprdShelf =
          WprdShelfHelper.appendStockIn(wprdIn.updateUser(), shelf, wprdIn, storeNum);
      this.wprdShelfRepository.save(wprdShelf);
      shelfList.add(wprdShelf);
    }

    return shelfList;
  }

  @Override
  public List<WprdShelf> exitShelfList(WprdOut wprdOut) {
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(wprdOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("废品出库 {0} 缺少 shelfJson 参数", wprdOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(WprdInHelper::extractShelfCode));

    Set<String> shelfCodes = outShelfMap.keySet();

    List<WprdShelf> shelfList = this.wprdShelfRepository.findAllByShelfCodeIn(shelfCodes);
    if (shelfList.isEmpty()) {
      throw new BadRequestException("获取到的库位信息为空");
    }

    logger.debug("废品库位表中修改出库表，获取到 {} 条库位: {}", shelfList.size(), shelfList);
    for (WprdShelf shelf : shelfList) {
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

      WprdShelfHelper.removeStockIn(wprdOut.updateUser(), shelf, wprdOut.inId(), deltaStockNum);
    }

    return this.wprdShelfRepository.saveAll(shelfList);
  }

  @Override
  public List<WprdIn> findShelfIns(Map<String, Object> params) {
    String roomCode =
        ParamReader.init(params)
            .stringFromKey("roomCode")
            .orElseThrow(() -> new BadRequestException("无法获取 roomCode 参数"));

    List<WprdShelf> shelfs = this.wprdShelfRepository.findAllByRoomCodeStartsWith(roomCode);
    List<Long> inIds =
        shelfs.stream().flatMap(WprdInHelper::extractInIds).distinct().collect(Collectors.toList());

    logger.debug("从 {} 条库位中获取到 {} 条废品入库列表", shelfs.size(), inIds.size());
    return this.wprdInRepository.findByIdIn(inIds);
  }
}
