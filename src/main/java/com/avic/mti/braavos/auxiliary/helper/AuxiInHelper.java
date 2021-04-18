package com.avic.mti.iron.auxiliary.helper;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiIn;
import com.avic.mti.iron.auxiliary.domain.entity.AuxiOut;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.ParamReader;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuxiInHelper {

  public static final Logger logger = LoggerFactory.getLogger(AuxiInHelper.class);

  public static AuxiIn assignAuxiIn(AuxiIn auxiIn, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(auxiIn::updateUser);

    reader.stringFromKey("inCode").ifPresent(auxiIn::inCode);
    reader.stringFromKey("inCate").ifPresent(auxiIn::inCate);
    reader.stringFromKey("inType").ifPresent(auxiIn::inType);
    reader.longFromKey("auxiId").ifPresent(auxiIn::auxiId);
    reader.stringFromKey("mtlBatch").ifPresent(auxiIn::mtlBatch);
    reader.stringFromKey("auxiName").ifPresent(auxiIn::auxiName);
    reader.stringFromKey("auxiSpec").ifPresent(auxiIn::auxiSpec);
    reader.stringFromKey("auxiType").ifPresent(auxiIn::auxiType);
    reader.stringFromKey("mtlMark").ifPresent(auxiIn::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(auxiIn::mtlStandard);
    reader.stringFromKey("bplnCode").ifPresent(auxiIn::bplnCode);
    reader.stringFromKey("purchCode").ifPresent(auxiIn::purchCode);
    reader.doubleFromKey("auxiPrice").ifPresent(auxiIn::auxiPrice);
    reader.longFromKey("auxiStkNum").ifPresent(auxiIn::auxiStkNum);
    reader.longFromKey("auxiAvlNum").ifPresent(auxiIn::auxiAvlNum);
    reader.longFromKey("auxiPlnNum").ifPresent(auxiIn::auxiPlnNum);
    reader.stringFromKey("measure").ifPresent(auxiIn::measure);
    reader.longFromKey("serviceLife").ifPresent(auxiIn::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(auxiIn::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(auxiIn::produceTime);
    reader.timeFromKey("expireTime").ifPresent(auxiIn::expireTime);
    reader.stringFromKey("auxiMaker").ifPresent(auxiIn::auxiMaker);
    reader.stringFromKey("inNC").ifPresent(auxiIn::inNC);
    reader.timeFromKey("inDate").ifPresent(auxiIn::inDate);
    reader.longFromKey("prevInId").ifPresent(auxiIn::prevInId);
    reader.stringFromKey("retestStatus").ifPresent(auxiIn::retestStatus);
    reader.longFromKey("retestId").ifPresent(auxiIn::retestId);
    reader.stringFromKey("shelfText").ifPresent(auxiIn::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(auxiIn::shelfJson);

    return auxiIn;
  }

  public static AuxiIn exitAuxiIn(String updateUser, AuxiIn auxiIn, AuxiOut auxiOut) {
    updateNumbers(auxiIn, -auxiOut.planEnum(), -auxiOut.planFnum(), 0L);
    return auxiIn;
  }

  public static AuxiIn updateNumbers(
      AuxiIn auxiIn, long deltaStkNum, long deltaAvlNum, long deltaPlnNum) {
    logger.trace(
        "开始更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        auxiIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    long newStkNum = auxiIn.auxiStkNum() + deltaStkNum;
    if (newStkNum < 0) {
      throw new BadRequestException("辅材入库 {0} 的库存数量不足", auxiIn);
    }

    auxiIn.auxiStkNum(newStkNum);

    long newAvlNum = auxiIn.auxiAvlNum() + deltaAvlNum;
    if (newAvlNum < 0) {
      throw new BadRequestException("辅材入库 {0} 的可用数量不足", auxiIn);
    }

    auxiIn.auxiAvlNum(newAvlNum);

    long newPlnNum = auxiIn.auxiPlnNum() + deltaPlnNum;
    if (newPlnNum < 0) {
      throw new BadRequestException("辅材入库 {0} 的计划数量不足", auxiIn);
    }

    auxiIn.auxiPlnNum(newPlnNum);

    logger.trace(
        "完成更新 {} 数值: ΔSTK = {}, ΔAVL = {}, ΔPLN = {}",
        auxiIn,
        deltaStkNum,
        deltaAvlNum,
        deltaPlnNum);

    return auxiIn;
  }

  private AuxiInHelper() {}
}
