package com.avic.mti.iron.waste.helper;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.DirtyDataException;
import com.avic.mti.iron.common.helper.JsonHelper;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdShelf;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WprdShelfHelper {

  public static WprdShelf assignWprdShelf(WprdShelf wprdShelf, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(wprdShelf::updateUser);
    reader.stringFromKey("roomCode").ifPresent(wprdShelf::roomCode);
    reader.stringFromKey("shelfCode").ifPresent(wprdShelf::shelfCode);
    reader.longFromKey("storeNum").ifPresent(wprdShelf::storeNum);
    reader.stringFromKey("measure").ifPresent(wprdShelf::measure);
    reader.stringFromKey("creatorNC").ifPresent(wprdShelf::creatorNC);
    reader.timeFromKey("createDate").ifPresent(wprdShelf::createDate);
    reader.stringFromKey("note").ifPresent(wprdShelf::note);
    reader.stringFromKey("detailJson").ifPresent(wprdShelf::detailJson);
    return wprdShelf;
  }

  public static WprdShelf appendStockIn(
      String updateUser, WprdShelf shelf, WprdIn wprdIn, long storeNum) {
    List<Map<String, Object>> detailList =
        JsonHelper.parseList(shelf.detailJson()).orElse(new LinkedList<>());

    Map<String, Object> detailParam =
        ParamBuilder.init()
            .put("inId", wprdIn.id())
            .put("inCode", wprdIn.inCode())
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
      throw new BadRequestException("废品库位 {0} 存入的库存数量小于零", shelf);
    }
    shelf.storeNum(totalStoreNum);

    shelf.updateUser(updateUser);
    return shelf;
  }

  public static WprdShelf removeStockIn(
      String updateUser, WprdShelf shelf, long inId, long deltaStoreNum) {
    List<Map<String, Object>> detailList =
        JsonHelper.parseList(shelf.detailJson())
            .orElseThrow(
                () -> new BadRequestException("废品库位 {0} 的 detailJson 参数出现问题", shelf.shelfCode()));

    int foundWprdCount = 0;
    List<Map<String, Object>> newDetailList = new LinkedList<>();
    for (Map<String, Object> detail : detailList) {
      ParamReader reader = ParamReader.init(detail);

      long shelfInId = reader.longFromKey("inId").orElse(-1L);
      if (shelfInId == inId) {
        foundWprdCount += 1;

        long storeNum =
            reader
                .longFromKey("storeNum")
                .orElseThrow(() -> new BadRequestException("JSON 对象中缺少 storeNum 键"));
        long newStoreNum = storeNum - deltaStoreNum;
        if (newStoreNum > 0) {
          detail.put("storeNum", newStoreNum);
          newDetailList.add(detail);
        } else if (newStoreNum < 0) {
          throw new BadRequestException("库位 {0} 的废品入库 {1,number,#} 的数量不足", shelf.shelfCode(), inId);
        }

      } else {
        newDetailList.add(detail);
      }
    }
    JsonHelper.stringifyList(newDetailList).ifPresent(shelf::detailJson);

    if (foundWprdCount > 1) {
      throw new DirtyDataException("库位 {0} 到多项的废品入库 {1,number,#} 的记录", shelf.shelfCode(), inId);
    } else if (foundWprdCount <= 0) {
      throw new BadRequestException("库位 {0} 找不到对应的废品入库 {1,number,#} 的记录", shelf.shelfCode(), inId);
    }

    long newTotalStoreNum = shelf.storeNum() - deltaStoreNum;
    if (newTotalStoreNum < 0) {
      throw new BadRequestException("库位 {0} 的库存数量不足", shelf.shelfCode());
    }
    shelf.storeNum(newTotalStoreNum);

    shelf.updateUser(updateUser);
    return shelf;
  }

  public WprdShelfHelper() {}
}
