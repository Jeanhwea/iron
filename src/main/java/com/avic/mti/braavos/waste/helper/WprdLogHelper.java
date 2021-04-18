package com.avic.mti.iron.waste.helper;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.waste.*;
import com.avic.mti.iron.waste.domain.entity.WprdIn;
import com.avic.mti.iron.waste.domain.entity.WprdLog;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import java.util.Map;

public class WprdLogHelper {

  public static WprdLog assignWprdLog(WprdLog wprdLog, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(wprdLog::updateUser);

    reader.stringFromKey("logStatus").ifPresent(wprdLog::logStatus);
    reader.stringFromKey("logCate").ifPresent(wprdLog::logCate);
    reader.stringFromKey("logType").ifPresent(wprdLog::logType);
    reader.stringFromKey("logFlag").ifPresent(wprdLog::logFlag);
    reader.longFromKey("wprdId").ifPresent(wprdLog::wprdId);
    reader.longFromKey("inId").ifPresent(wprdLog::inId);
    reader.longFromKey("stubId").ifPresent(wprdLog::stubId);
    reader.longFromKey("stubDetId").ifPresent(wprdLog::stubDetId);
    reader.longFromKey("planEnum").ifPresent(wprdLog::planEnum);
    reader.longFromKey("planFnum").ifPresent(wprdLog::planFnum);
    reader.longFromKey("retNum").ifPresent(wprdLog::retNum);
    reader.longFromKey("retPrevId").ifPresent(wprdLog::retPrevId);
    reader.stringFromKey("measure").ifPresent(wprdLog::measure);
    reader.stringFromKey("mtlBatch").ifPresent(wprdLog::mtlBatch);
    reader.longFromKey("serviceLife").ifPresent(wprdLog::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(wprdLog::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(wprdLog::produceTime);
    reader.timeFromKey("expireTime").ifPresent(wprdLog::expireTime);
    reader.stringFromKey("projCode").ifPresent(wprdLog::projCode);
    reader.stringFromKey("taskCode").ifPresent(wprdLog::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(wprdLog::brgnCode);
    reader.stringFromKey("outReason").ifPresent(wprdLog::outReason);
    reader.stringFromKey("creatorNC").ifPresent(wprdLog::creatorNC);
    reader.timeFromKey("createDate").ifPresent(wprdLog::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(wprdLog::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(wprdLog::finishDate);
    reader.stringFromKey("note").ifPresent(wprdLog::note);

    return wprdLog;
  }

  public static WprdLog consWprdLogForIn(WprdLog wprdLog, WprdIn wprdIn) {
    wprdLog.updateUser(wprdIn.updateUser());
    wprdLog.logStatus(WprdLogStatusEnum.Enum1_YSX.symbol());

    String logCate = null;
    switch (WprdInCateEnum.of(wprdIn.inCate())) {
      case Enum1_RK:
        logCate = WprdLogCateEnum.Enum01_RK.symbol();
        break;
      case Enum2_GH:
        logCate = WprdLogCateEnum.Enum02_GHRK.symbol();
        break;
      case Enum3_YK:
        logCate = WprdLogCateEnum.Enum03_YK.symbol();
        break;
      case Enum9_QT:
        logCate = WprdLogCateEnum.Enum09_YK.symbol();
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的入库类型: {0}", wprdIn.inCate());
    }
    wprdLog.logCate(logCate);

    wprdLog.wprdId(wprdIn.wprdId());
    wprdLog.inId(wprdIn.id());
    wprdLog.measure(wprdIn.measure());
    wprdLog.mtlBatch(wprdIn.mtlBatch());
    wprdLog.serviceLife(wprdIn.serviceLife());
    wprdLog.produceTime(wprdIn.produceTime());
    wprdLog.expireTime(wprdIn.expireTime());
    wprdLog.creatorNC(wprdIn.inNC());
    wprdLog.createDate(wprdIn.inDate());
    wprdLog.planEnum(wprdIn.wprdPlnNum());
    wprdLog.planFnum(wprdIn.wprdStkNum());
    return wprdLog;
  }

  public static WprdLog consWprdLogForOut(WprdLog wprdLog, WprdOut wprdOut) {
    wprdLog.updateUser(wprdOut.updateUser());
    wprdLog.logStatus(WprdLogStatusEnum.Enum1_YSX.symbol());

    String logCate = null;
    switch (WprdOutCateEnum.of(wprdOut.outCate())) {
      case Enum1_JY:
        logCate = WprdLogCateEnum.Enum11_JY.symbol();
        break;
      case Enum2_LY:
        logCate = WprdLogCateEnum.Enum12_LY.symbol();
        break;
      case Enum3_LXJY:
        logCate = WprdLogCateEnum.Enum13_LXJY.symbol();
        break;
      case Enum4_LXLY:
        logCate = WprdLogCateEnum.Enum14_LXLY.symbol();
        break;
      case Enum5_BF:
        logCate = WprdLogCateEnum.Enum15_BF.symbol();
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类型: {0}", wprdOut.outCate());
    }
    wprdLog.logCate(logCate);

    WprdLogTypeEnum logType = WprdLogTypeEnum.Unknown;
    switch (WprdOutTypeEnum.of(wprdOut.outType())) {
      case Enum1_ZDCK:
        logType = WprdLogTypeEnum.Enum1_ZDCK;
        break;
      case Enum2_ZJCK:
        logType = WprdLogTypeEnum.Enum2_ZJCK;
        break;
      case Enum3_BFCK:
        logType = WprdLogTypeEnum.Enum3_BFCK;
        break;
      case Enum4_YKCK:
        logType = WprdLogTypeEnum.Enum4_YKCK;
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类型: {0}", wprdOut.outCate());
    }
    wprdLog.logType(logType.symbol());

    wprdLog.logFlag(wprdOut.outFlag());
    wprdLog.wprdId(wprdOut.wprdId());
    wprdLog.inId(wprdOut.inId());
    wprdLog.measure(wprdOut.measure());
    wprdLog.mtlBatch(wprdOut.mtlBatch());
    wprdLog.serviceLife(wprdOut.serviceLife());
    wprdLog.serviceLife2(wprdOut.serviceLife2());
    wprdLog.produceTime(wprdOut.produceTime());
    wprdLog.expireTime(wprdOut.expireTime());
    wprdLog.creatorNC(wprdOut.creatorNC());
    wprdLog.createDate(wprdOut.createDate());
    wprdLog.planEnum(wprdOut.planEnum());
    wprdLog.planFnum(wprdOut.planFnum());
    wprdLog.outReason(wprdOut.outReason());
    wprdLog.stubId(wprdOut.stubId());
    wprdLog.stubDetId(wprdOut.stubDetId());
    wprdLog.finishDate(wprdOut.finishDate());
    wprdLog.finishOperNC(wprdOut.finishOperNC());
    wprdLog.note(wprdOut.note());
    wprdLog.taskCode(wprdOut.taskCode());
    wprdLog.brgnCode(wprdOut.brgnCode());
    wprdLog.projCode(wprdOut.projCode());
    return wprdLog;
  }

  private WprdLogHelper() {}
}
