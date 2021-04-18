package com.avic.mti.iron.measure.helper;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import com.avic.mti.iron.measure.domain.entity.MeasShelf;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasInHelper {

  public static final Logger logger = LoggerFactory.getLogger(MeasInHelper.class);

  public static MeasIn assignMeasIn(MeasIn measIn, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(measIn::updateUser);

    reader.stringFromKey("inCode").ifPresent(measIn::inCode);
    reader.stringFromKey("inCate").ifPresent(measIn::inCate);
    reader.stringFromKey("inType").ifPresent(measIn::inType);
    reader.longFromKey("measId").ifPresent(measIn::measId);
    reader.stringFromKey("mtlBatch").ifPresent(measIn::mtlBatch);
    reader.stringFromKey("measName").ifPresent(measIn::measName);
    reader.stringFromKey("measSpec").ifPresent(measIn::measSpec);
    reader.stringFromKey("measUsage").ifPresent(measIn::measUsage);
    reader.stringFromKey("measType").ifPresent(measIn::measType);
    reader.stringFromKey("mtlMark").ifPresent(measIn::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(measIn::mtlStandard);
    reader.stringFromKey("bplnCode").ifPresent(measIn::bplnCode);
    reader.stringFromKey("purchCode").ifPresent(measIn::purchCode);
    reader.doubleFromKey("measPrice").ifPresent(measIn::measPrice);
    reader.longFromKey("measStkNum").ifPresent(measIn::measStkNum);
    reader.longFromKey("measAvlNum").ifPresent(measIn::measAvlNum);
    reader.longFromKey("measPlnNum").ifPresent(measIn::measPlnNum);
    reader.stringFromKey("measure").ifPresent(measIn::measure);
    reader.longFromKey("serviceLife").ifPresent(measIn::serviceLife);
    reader.timeFromKey("produceTime").ifPresent(measIn::produceTime);
    reader.timeFromKey("expireTime").ifPresent(measIn::expireTime);
    reader.stringFromKey("measMaker").ifPresent(measIn::measMaker);
    reader.stringFromKey("inNC").ifPresent(measIn::inNC);
    reader.timeFromKey("inDate").ifPresent(measIn::inDate);
    reader.longFromKey("prevInId").ifPresent(measIn::prevInId);
    reader.stringFromKey("retestStatus").ifPresent(measIn::retestStatus);
    reader.longFromKey("retestId").ifPresent(measIn::retestId);
    reader.stringFromKey("shelfText").ifPresent(measIn::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(measIn::shelfJson);

    return measIn;
  }

  private static Long readShelfInId(Map<String, Object> obj) {
    return ParamReader.init(obj)
        .longFromKey("inId")
        .orElseThrow(() -> new BadRequestException("入库的 detailJson 字典中缺少 inId 键"));
  }

  public static Stream<Long> extractInIds(MeasShelf measShelf) {
    List<Map<String, Object>> detailJson =
        JsonHelper.parseList(measShelf.detailJson())
            .orElseThrow(() -> new BadRequestException("无法解析入库的 detailJson 参数"));
    return detailJson.stream().map(MeasInHelper::readShelfInId);
  }

  public static String extractShelfCode(Map<String, Object> shelf) {
    return ParamReader.init(shelf)
        .stringFromKey("shelfCode")
        .orElseThrow(() -> new BadRequestException("参数 shelfJson 缺少 shelfCode 的键"));
  }

  public static MeasIn exitMeasIn(String updateUser, MeasIn measIn, MeasOut measOut) {
    List<Map<String, Object>> inShelfList =
        JsonHelper.parseList(measIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("工具入库 {0} 缺少 shelfJson 参数", measIn));
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(measOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("工具出库 {0} 缺少 shelfJson 参数", measOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(MeasInHelper::extractShelfCode));

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
          throw new BadRequestException("工具入库 {0} 对应的 {1} 库位数量不足", measIn, currShelfCode);
        } else {
          logger.trace("工具入库 {} 对应的 {} 库位库存数量为零，直接删除入库项目", measIn, currShelfCode);
        }
      }
      outShelfCodes.remove(currShelfCode);
    }

    if (!outShelfCodes.isEmpty()) {
      throw new BadRequestException(
          "工具出库编码存在 {0} 条入库中不存在的项目: {1}", outShelfCodes.size(), outShelfCodes);
    }
    JsonHelper.stringifyList(newInShelfList).ifPresent(measIn::shelfJson);

    if (totalDeltaStoreNum < 0) {
      throw new BadRequestException("出库数量汇总值小于零: totalDeltaStoreNum = {0}", totalDeltaStoreNum);
    }

    updateNumbers(measIn, -totalDeltaStoreNum, -totalDeltaStoreNum, 0);

    measIn.updateUser(updateUser);
    return measIn;
  }

  public static MeasIn updateNumbers(
      MeasIn measIn, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    logger.trace(
        "开始更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        measIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    long newStkNum = measIn.measStkNum() + deltaStkNum;
    if (newStkNum < 0) {
      throw new BadRequestException("工具入库 {0} 的库存数量不足", measIn);
    }

    measIn.measStkNum(newStkNum);

    long newAvlNum = measIn.measAvlNum() + deltaAvlNum;
    if (newAvlNum < 0) {
      throw new BadRequestException("工具入库 {0} 的可用数量不足", measIn);
    }

    measIn.measAvlNum(newAvlNum);

    long newPlnNum = measIn.measPlnNum() + deltaPlnNum;
    if (newPlnNum < 0) {
      throw new BadRequestException("工具入库 {0} 的计划数量不足", measIn);
    }

    measIn.measPlnNum(newPlnNum);

    logger.trace(
        "完成更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        measIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    return measIn;
  }

  private MeasInHelper() {}
}
