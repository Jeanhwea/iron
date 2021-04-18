package com.avic.mti.iron.waste.helper;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import com.avic.mti.iron.waste.domain.entity.WprdShelf;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WprdInHelper {

  public static final Logger logger = LoggerFactory.getLogger(WprdInHelper.class);

  public static WprdIn assignWprdIn(WprdIn wprdIn, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(wprdIn::updateUser);

    reader.stringFromKey("inCode").ifPresent(wprdIn::inCode);
    reader.stringFromKey("inCate").ifPresent(wprdIn::inCate);
    reader.stringFromKey("inType").ifPresent(wprdIn::inType);
    reader.longFromKey("wprdId").ifPresent(wprdIn::wprdId);
    reader.stringFromKey("mtlBatch").ifPresent(wprdIn::mtlBatch);
    reader.stringFromKey("wprdName").ifPresent(wprdIn::wprdName);
    reader.stringFromKey("wprdSpec").ifPresent(wprdIn::wprdSpec);
    reader.stringFromKey("wprdCate").ifPresent(wprdIn::wprdCate);
    reader.stringFromKey("wprdType").ifPresent(wprdIn::wprdType);
    reader.stringFromKey("mtlMark").ifPresent(wprdIn::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(wprdIn::mtlStandard);
    reader.stringFromKey("bplnCode").ifPresent(wprdIn::bplnCode);
    reader.stringFromKey("purchCode").ifPresent(wprdIn::purchCode);
    reader.doubleFromKey("wprdPrice").ifPresent(wprdIn::wprdPrice);
    reader.longFromKey("wprdStkNum").ifPresent(wprdIn::wprdStkNum);
    reader.longFromKey("wprdAvlNum").ifPresent(wprdIn::wprdAvlNum);
    reader.longFromKey("wprdPlnNum").ifPresent(wprdIn::wprdPlnNum);
    reader.stringFromKey("measure").ifPresent(wprdIn::measure);
    reader.longFromKey("serviceLife").ifPresent(wprdIn::serviceLife);
    reader.timeFromKey("produceTime").ifPresent(wprdIn::produceTime);
    reader.timeFromKey("expireTime").ifPresent(wprdIn::expireTime);
    reader.stringFromKey("wprdMaker").ifPresent(wprdIn::wprdMaker);
    reader.stringFromKey("inNC").ifPresent(wprdIn::inNC);
    reader.timeFromKey("inDate").ifPresent(wprdIn::inDate);
    reader.longFromKey("prevInId").ifPresent(wprdIn::prevInId);
    reader.stringFromKey("note").ifPresent(wprdIn::note);
    reader.longFromKey("retestId").ifPresent(wprdIn::retestId);
    reader.stringFromKey("shelfText").ifPresent(wprdIn::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(wprdIn::shelfJson);

    return wprdIn;
  }

  private static Long readShelfInId(Map<String, Object> obj) {
    return ParamReader.init(obj)
        .longFromKey("inId")
        .orElseThrow(() -> new BadRequestException("入库的 detailJson 字典中缺少 inId 键"));
  }

  public static Stream<Long> extractInIds(WprdShelf wprdShelf) {
    List<Map<String, Object>> detailJson =
        JsonHelper.parseList(wprdShelf.detailJson())
            .orElseThrow(() -> new BadRequestException("无法解析入库的 detailJson 参数"));
    return detailJson.stream().map(WprdInHelper::readShelfInId);
  }

  public static String extractShelfCode(Map<String, Object> shelf) {
    return ParamReader.init(shelf)
        .stringFromKey("shelfCode")
        .orElseThrow(() -> new BadRequestException("参数 shelfJson 缺少 shelfCode 的键"));
  }

  public static WprdIn exitWprdIn(String updateUser, WprdIn wprdIn, WprdOut wprdOut) {
    List<Map<String, Object>> inShelfList =
        JsonHelper.parseList(wprdIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("废品入库 {0} 缺少 shelfJson 参数", wprdIn));
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(wprdOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("废品出库 {0} 缺少 shelfJson 参数", wprdOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(WprdInHelper::extractShelfCode));

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
          throw new BadRequestException("废品入库 {0} 对应的 {1} 库位数量不足", wprdIn, currShelfCode);
        } else {
          logger.trace("废品入库 {} 对应的 {} 库位库存数量为零，直接删除入库项目", wprdIn, currShelfCode);
        }
      }
      outShelfCodes.remove(currShelfCode);
    }

    if (!outShelfCodes.isEmpty()) {
      throw new BadRequestException(
          "废品出库编码存在 {0} 条入库中不存在的项目: {1}", outShelfCodes.size(), outShelfCodes);
    }
    JsonHelper.stringifyList(newInShelfList).ifPresent(wprdIn::shelfJson);

    if (totalDeltaStoreNum < 0) {
      throw new BadRequestException("出库数量汇总值小于零: totalDeltaStoreNum = {0}", totalDeltaStoreNum);
    }

    updateNumbers(wprdIn, -totalDeltaStoreNum, -totalDeltaStoreNum, 0);

    wprdIn.updateUser(updateUser);
    return wprdIn;
  }

  public static WprdIn updateNumbers(
      WprdIn wprdIn, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    logger.trace(
        "开始更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        wprdIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    long newStkNum = wprdIn.wprdStkNum() + deltaStkNum;
    if (newStkNum < 0) {
      throw new BadRequestException("废品入库 {0} 的库存数量不足", wprdIn);
    }

    wprdIn.wprdStkNum(newStkNum);

    long newAvlNum = wprdIn.wprdAvlNum() + deltaAvlNum;
    if (newAvlNum < 0) {
      throw new BadRequestException("废品入库 {0} 的可用数量不足", wprdIn);
    }

    wprdIn.wprdAvlNum(newAvlNum);

    long newPlnNum = wprdIn.wprdPlnNum() + deltaPlnNum;
    if (newPlnNum < 0) {
      throw new BadRequestException("废品入库 {0} 的计划数量不足", wprdIn);
    }

    wprdIn.wprdPlnNum(newPlnNum);

    logger.trace(
        "完成更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        wprdIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    return wprdIn;
  }

  private WprdInHelper() {}
}
