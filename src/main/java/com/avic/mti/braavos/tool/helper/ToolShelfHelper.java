package com.avic.mti.iron.tool.helper;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DirtyDataException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolShelf;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ToolShelfHelper {

  public static ToolShelf assignToolShelf(ToolShelf toolShelf, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(toolShelf::updateUser);
    reader.stringFromKey("roomCode").ifPresent(toolShelf::roomCode);
    reader.stringFromKey("shelfCode").ifPresent(toolShelf::shelfCode);
    reader.longFromKey("storeNum").ifPresent(toolShelf::storeNum);
    reader.stringFromKey("measure").ifPresent(toolShelf::measure);
    reader.stringFromKey("creatorNC").ifPresent(toolShelf::creatorNC);
    reader.timeFromKey("createDate").ifPresent(toolShelf::createDate);
    reader.stringFromKey("note").ifPresent(toolShelf::note);
    reader.stringFromKey("detailJson").ifPresent(toolShelf::detailJson);
    return toolShelf;
  }

  public static ToolShelf appendStockIn(
      String updateUser, ToolShelf shelf, ToolIn toolIn, long storeNum) {
    List<Map<String, Object>> detailList =
        JsonHelper.parseList(shelf.detailJson()).orElse(new LinkedList<>());

    Map<String, Object> detailParam =
        ParamBuilder.init()
            .put("inId", toolIn.id())
            .put("inCode", toolIn.inCode())
            .put("storeNum", storeNum)
            .params();

    detailList.add(detailParam);
    shelf.detailJson(JsonHelper.stringifyList(detailList).orElse(null));

    long totalStoreNum = 0L;
    for (Map<String, Object> detail : detailList) {
      totalStoreNum +=
          ParamReader.init(detail)
              .longFromKey("storeNum")
              .orElseThrow(() -> new BadRequestException("JSON 对象中缺少 storeNum 键"));
    }
    if (totalStoreNum < 0) {
      throw new BadRequestException("工具库位 {0} 存入的库存数量小于零", shelf);
    }
    shelf.storeNum(totalStoreNum);

    shelf.updateUser(updateUser);
    return shelf;
  }

  public static ToolShelf removeStockIn(
      String updateUser, ToolShelf shelf, long inId, long deltaStoreNum) {
    List<Map<String, Object>> detailList =
        JsonHelper.parseList(shelf.detailJson())
            .orElseThrow(
                () -> new BadRequestException("工具库位 {0} 的 detailJson 参数出现问题", shelf.shelfCode()));

    int foundToolCount = 0;
    List<Map<String, Object>> newDetailList = new LinkedList<>();
    for (Map<String, Object> detail : detailList) {
      ParamReader reader = ParamReader.init(detail);

      long shelfInId = reader.longFromKey("inId").orElse(-1L);
      if (shelfInId == inId) {
        foundToolCount += 1;

        long storeNum =
            reader
                .longFromKey("storeNum")
                .orElseThrow(() -> new BadRequestException("JSON 对象中缺少 storeNum 键"));
        long newStoreNum = storeNum - deltaStoreNum;
        if (newStoreNum > 0) {
          detail.put("storeNum", newStoreNum);
          newDetailList.add(detail);
        } else if (newStoreNum < 0) {
          throw new BadRequestException("库位 {0} 的工具入库 {1,number,#} 的数量不足", shelf.shelfCode(), inId);
        }

      } else {
        newDetailList.add(detail);
      }
    }
    JsonHelper.stringifyList(newDetailList).ifPresent(shelf::detailJson);

    if (foundToolCount > 1) {
      throw new DirtyDataException("库位 {0} 到多项的工具入库 {1,number,#} 的记录", shelf.shelfCode(), inId);
    } else if (foundToolCount <= 0) {
      throw new BadRequestException("库位 {0} 找不到对应的工具入库 {1,number,#} 的记录", shelf.shelfCode(), inId);
    }

    long newTotalStoreNum = shelf.storeNum() - deltaStoreNum;
    if (newTotalStoreNum < 0) {
      throw new BadRequestException("库位 {0} 的库存数量不足", shelf.shelfCode());
    }
    shelf.storeNum(newTotalStoreNum);

    shelf.updateUser(updateUser);
    return shelf;
  }

  public ToolShelfHelper() {}
}
