package com.avic.mti.iron.tool.helper;

import static java.util.stream.Collectors.groupingBy;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import com.avic.mti.iron.tool.domain.entity.ToolShelf;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolInHelper {

  public static final Logger logger = LoggerFactory.getLogger(ToolInHelper.class);

  public static ToolIn assignToolIn(ToolIn toolIn, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(toolIn::updateUser);

    reader.stringFromKey("inCode").ifPresent(toolIn::inCode);
    reader.stringFromKey("inCate").ifPresent(toolIn::inCate);
    reader.stringFromKey("inType").ifPresent(toolIn::inType);
    reader.longFromKey("toolId").ifPresent(toolIn::toolId);
    reader.stringFromKey("mtlBatch").ifPresent(toolIn::mtlBatch);
    reader.stringFromKey("toolName").ifPresent(toolIn::toolName);
    reader.stringFromKey("toolSpec").ifPresent(toolIn::toolSpec);
    reader.stringFromKey("toolType").ifPresent(toolIn::toolType);
    reader.stringFromKey("mtlMark").ifPresent(toolIn::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(toolIn::mtlStandard);
    reader.stringFromKey("bplnCode").ifPresent(toolIn::bplnCode);
    reader.stringFromKey("purchCode").ifPresent(toolIn::purchCode);
    reader.doubleFromKey("toolPrice").ifPresent(toolIn::toolPrice);
    reader.longFromKey("toolStkNum").ifPresent(toolIn::toolStkNum);
    reader.longFromKey("toolAvlNum").ifPresent(toolIn::toolAvlNum);
    reader.longFromKey("toolPlnNum").ifPresent(toolIn::toolPlnNum);
    reader.stringFromKey("measure").ifPresent(toolIn::measure);
    reader.longFromKey("serviceLife").ifPresent(toolIn::serviceLife);
    reader.timeFromKey("produceTime").ifPresent(toolIn::produceTime);
    reader.timeFromKey("expireTime").ifPresent(toolIn::expireTime);
    reader.stringFromKey("toolMaker").ifPresent(toolIn::toolMaker);
    reader.stringFromKey("inNC").ifPresent(toolIn::inNC);
    reader.timeFromKey("inDate").ifPresent(toolIn::inDate);
    reader.longFromKey("prevInId").ifPresent(toolIn::prevInId);
    reader.stringFromKey("retestStatus").ifPresent(toolIn::retestStatus);
    reader.longFromKey("retestId").ifPresent(toolIn::retestId);
    reader.stringFromKey("shelfText").ifPresent(toolIn::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(toolIn::shelfJson);

    return toolIn;
  }

  private static Long readShelfInId(Map<String, Object> obj) {
    return ParamReader.init(obj)
        .longFromKey("inId")
        .orElseThrow(() -> new BadRequestException("入库的 detailJson 字典中缺少 inId 键"));
  }

  public static Stream<Long> extractInIds(ToolShelf toolShelf) {
    List<Map<String, Object>> detailJson =
        JsonHelper.parseList(toolShelf.detailJson())
            .orElseThrow(() -> new BadRequestException("无法解析入库的 detailJson 参数"));
    return detailJson.stream().map(ToolInHelper::readShelfInId);
  }

  public static String extractShelfCode(Map<String, Object> shelf) {
    return ParamReader.init(shelf)
        .stringFromKey("shelfCode")
        .orElseThrow(() -> new BadRequestException("参数 shelfJson 缺少 shelfCode 的键"));
  }

  public static ToolIn exitToolIn(String updateUser, ToolIn toolIn, ToolOut toolOut) {
    List<Map<String, Object>> inShelfList =
        JsonHelper.parseList(toolIn.shelfJson())
            .orElseThrow(() -> new BadRequestException("工具入库 {0} 缺少 shelfJson 参数", toolIn));
    List<Map<String, Object>> outShelfList =
        JsonHelper.parseList(toolOut.shelfJson())
            .orElseThrow(() -> new BadRequestException("工具出库 {0} 缺少 shelfJson 参数", toolOut));

    Map<String, List<Map<String, Object>>> outShelfMap =
        outShelfList.stream().collect(groupingBy(ToolInHelper::extractShelfCode));

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
          throw new BadRequestException("工具入库 {0} 对应的 {1} 库位数量不足", toolIn, currShelfCode);
        } else {
          logger.trace("工具入库 {} 对应的 {} 库位库存数量为零，直接删除入库项目", toolIn, currShelfCode);
        }
      }
      outShelfCodes.remove(currShelfCode);
    }

    if (!outShelfCodes.isEmpty()) {
      throw new BadRequestException(
          "工具出库编码存在 {0} 条入库中不存在的项目: {1}", outShelfCodes.size(), outShelfCodes);
    }
    JsonHelper.stringifyList(newInShelfList).ifPresent(toolIn::shelfJson);

    if (totalDeltaStoreNum < 0) {
      throw new BadRequestException("出库数量汇总值小于零: totalDeltaStoreNum = {0}", totalDeltaStoreNum);
    }

    updateNumbers(toolIn, -totalDeltaStoreNum, -totalDeltaStoreNum, 0);

    toolIn.updateUser(updateUser);
    return toolIn;
  }

  public static ToolIn updateNumbers(
      ToolIn toolIn, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    logger.trace(
        "开始更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        toolIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    long newStkNum = toolIn.toolStkNum() + deltaStkNum;
    if (newStkNum < 0) {
      throw new BadRequestException("工具入库 {0} 的库存数量不足", toolIn);
    }

    toolIn.toolStkNum(newStkNum);

    long newAvlNum = toolIn.toolAvlNum() + deltaAvlNum;
    if (newAvlNum < 0) {
      throw new BadRequestException("工具入库 {0} 的可用数量不足", toolIn);
    }

    toolIn.toolAvlNum(newAvlNum);

    long newPlnNum = toolIn.toolPlnNum() + deltaPlnNum;
    if (newPlnNum < 0) {
      throw new BadRequestException("工具入库 {0} 的计划数量不足", toolIn);
    }

    toolIn.toolPlnNum(newPlnNum);

    logger.trace(
        "完成更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        toolIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    return toolIn;
  }

  private ToolInHelper() {}
}
