package com.avic.mti.iron.main.helper;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainOut;
import com.avic.mti.iron.main.domain.entity.MainShelf;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainInHelper {

  public static final Logger logger = LoggerFactory.getLogger(MainInHelper.class);

  public static MainIn assignMainIn(MainIn mainIn, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(mainIn::updateUser);

    reader.stringFromKey("inCode").ifPresent(mainIn::inCode);
    reader.stringFromKey("inCate").ifPresent(mainIn::inCate);
    reader.stringFromKey("inType").ifPresent(mainIn::inType);
    reader.longFromKey("mainId").ifPresent(mainIn::mainId);
    reader.stringFromKey("mtlBatch").ifPresent(mainIn::mtlBatch);
    reader.stringFromKey("mainName").ifPresent(mainIn::mainName);
    reader.stringFromKey("mainSpec").ifPresent(mainIn::mainSpec);
    reader.stringFromKey("mainType").ifPresent(mainIn::mainType);
    reader.stringFromKey("mtlMark").ifPresent(mainIn::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(mainIn::mtlStandard);
    reader.stringFromKey("bplnCode").ifPresent(mainIn::bplnCode);
    reader.stringFromKey("purchCode").ifPresent(mainIn::purchCode);
    reader.doubleFromKey("mainPrice").ifPresent(mainIn::mainPrice);
    reader.longFromKey("mainStkNum").ifPresent(mainIn::mainStkNum);
    reader.longFromKey("mainAvlNum").ifPresent(mainIn::mainAvlNum);
    reader.longFromKey("mainPlnNum").ifPresent(mainIn::mainPlnNum);
    reader.stringFromKey("measure").ifPresent(mainIn::measure);
    reader.longFromKey("serviceLife").ifPresent(mainIn::serviceLife);
    reader.timeFromKey("produceTime").ifPresent(mainIn::produceTime);
    reader.timeFromKey("expireTime").ifPresent(mainIn::expireTime);
    reader.stringFromKey("mainMaker").ifPresent(mainIn::mainMaker);
    reader.stringFromKey("inNC").ifPresent(mainIn::inNC);
    reader.timeFromKey("inDate").ifPresent(mainIn::inDate);
    reader.longFromKey("prevInId").ifPresent(mainIn::prevInId);
    reader.stringFromKey("retestStatus").ifPresent(mainIn::retestStatus);
    reader.longFromKey("retestId").ifPresent(mainIn::retestId);
    reader.stringFromKey("shelfJson").ifPresent(mainIn::shelfJson);

    reader.doubleFromKey("mainVar01Num").ifPresent(mainIn::mainVar01Num);
    reader.doubleFromKey("mainVar02Num").ifPresent(mainIn::mainVar02Num);
    reader.longFromKey("mainVar03Num").ifPresent(mainIn::mainVar03Num);
    reader.longFromKey("mainVar04Num").ifPresent(mainIn::mainVar04Num);
    reader.stringFromKey("mainVar01Str").ifPresent(mainIn::mainVar01Str);
    reader.stringFromKey("mainVar02Str").ifPresent(mainIn::mainVar02Str);
    reader.stringFromKey("mainVar03Str").ifPresent(mainIn::mainVar03Str);
    reader.stringFromKey("mainVar04Str").ifPresent(mainIn::mainVar04Str);
    reader.stringFromKey("mainVar05Str").ifPresent(mainIn::mainVar05Str);
    reader.stringFromKey("mainVar06Str").ifPresent(mainIn::mainVar06Str);
    reader.stringFromKey("mainVar07Str").ifPresent(mainIn::mainVar07Str);
    reader.stringFromKey("mainVar08Str").ifPresent(mainIn::mainVar08Str);
    reader.stringFromKey("mainExt01Json").ifPresent(mainIn::mainExt01Json);
    reader.stringFromKey("mainExt02Json").ifPresent(mainIn::mainExt02Json);
    reader.stringFromKey("mainExt03Json").ifPresent(mainIn::mainExt03Json);
    reader.stringFromKey("mainExt04Json").ifPresent(mainIn::mainExt04Json);

    return mainIn;
  }

  private static Long readShelfInId(Map<String, Object> obj) {
    return ParamReader.init(obj)
        .longFromKey("inId")
        .orElseThrow(() -> new BadRequestException("入库的 detailJson 字典中缺少 inId 键"));
  }

  public static Stream<Long> extractInIds(MainShelf mainShelf) {
    List<Map<String, Object>> detailJson =
        JsonHelper.parseList(mainShelf.detailJson())
            .orElseThrow(() -> new BadRequestException("无法解析入库的 detailJson 参数"));
    return detailJson.stream().map(MainInHelper::readShelfInId);
  }

  public static String extractShelfCode(Map<String, Object> shelf) {
    return ParamReader.init(shelf)
        .stringFromKey("shelfCode")
        .orElseThrow(() -> new BadRequestException("参数 shelfJson 缺少 shelfCode 的键"));
  }

  public static MainIn exitMainIn(String updateUser, MainIn mainIn, MainOut mainOut) {
    List<Map<String, Object>> inShelfList =
        JsonHelper.parseList(mainIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("主材入库 {0} 缺少 shelfJson 参数", mainIn));
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(mainOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("主材出库 {0} 缺少 shelfJson 参数", mainOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(MainInHelper::extractShelfCode));

    long totalDeltaStoreNum = 0L;
    Set<String> outShelfCodes = outShelfMap.keySet();
    List<Map<String, Object>> newInShelfList = new LinkedList<>();
    for (Map<String, Object> inShelf : inShelfList) {
      ParamReader reader = ParamReader.init(inShelf);
      String currShelfCode =
          reader
              .stringFromKey("shelfCode")
              .orElseThrow(() -> new BadRequestException("参数 shelfJson 缺少 shelfCode 的键"));
      long storeNum =
          reader
              .longFromKey("storeNum")
              .orElseThrow(() -> new BadRequestException("缺少 storeNum 参数"));

      List<Map<String, Object>> outs = outShelfMap.get(currShelfCode);
      if (outs == null || outs.isEmpty()) {
        newInShelfList.add(inShelf);
      } else {
        long deltaStoreNum =
            outs.stream()
                .map(o -> ParamReader.init(o).longFromKey("storeNum").orElse(0L))
                .reduce(Long::sum)
                .orElse(0L);
        totalDeltaStoreNum += deltaStoreNum;
        long newStoreNum = storeNum - deltaStoreNum;
        if (newStoreNum > 0) {
          inShelf.put("storeNum", newStoreNum);
          newInShelfList.add(inShelf);
        } else if (newStoreNum < 0) {
          throw new BadRequestException("主材入库 {0} 对应的 {1} 库位数量不足", mainIn, currShelfCode);
        } else {
          logger.trace("主材入库 {} 对应的 {} 库位库存数量为零，直接删除入库项目", mainIn, currShelfCode);
        }
      }
      outShelfCodes.remove(currShelfCode);
    }

    if (!outShelfCodes.isEmpty()) {
      throw new BadRequestException(
          "主材出库编码存在 {0} 条入库中不存在的项目: {1}", outShelfCodes.size(), outShelfCodes);
    }
    JsonHelper.stringifyList(newInShelfList).ifPresent(mainIn::shelfJson);

    if (totalDeltaStoreNum < 0) {
      throw new BadRequestException("出库数量汇总值小于零: totalDeltaStoreNum = {0}", totalDeltaStoreNum);
    }

    updateNumbers(mainIn, -totalDeltaStoreNum, -totalDeltaStoreNum, -totalDeltaStoreNum);

    mainIn.updateUser(updateUser);
    return mainIn;
  }

  public static MainIn updateNumbers(
      MainIn mainIn, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    logger.trace(
        "开始更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        mainIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    long newStkNum = mainIn.mainStkNum() + deltaStkNum;
    if (newStkNum < 0) {
      throw new BadRequestException("主材入库 {0} 的库存数量不足", mainIn);
    }

    mainIn.mainStkNum(newStkNum);

    long newAvlNum = mainIn.mainAvlNum() + deltaAvlNum;
    if (newAvlNum < 0) {
      throw new BadRequestException("主材入库 {0} 的可用数量不足", mainIn);
    }

    mainIn.mainAvlNum(newAvlNum);

    long newPlnNum = mainIn.mainPlnNum() + deltaPlnNum;
    if (newPlnNum < 0) {
      // throw new BadRequestException("主材入库 {0} 的计划数量不足", mainIn);
      newPlnNum = 0L;
      logger.warn("主材入库 {} 的计划数量不足，默认填补成零", mainIn);
    }

    mainIn.mainPlnNum(newPlnNum);

    logger.trace(
        "完成更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        mainIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    return mainIn;
  }

  private MainInHelper() {}
}
