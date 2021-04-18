package com.avic.mti.iron.main.helper;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.main.*;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainLog;
import com.avic.mti.iron.main.domain.entity.MainOut;
import java.util.Map;

public class MainLogHelper {

  public static MainLog assignMainLog(MainLog mainLog, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(mainLog::updateUser);

    reader.stringFromKey("logStatus").ifPresent(mainLog::logStatus);
    reader.stringFromKey("logCate").ifPresent(mainLog::logCate);
    reader.stringFromKey("logType").ifPresent(mainLog::logType);
    reader.stringFromKey("logFlag").ifPresent(mainLog::logFlag);
    reader.longFromKey("mainId").ifPresent(mainLog::mainId);
    reader.longFromKey("inId").ifPresent(mainLog::inId);
    reader.longFromKey("stubId").ifPresent(mainLog::stubId);
    reader.longFromKey("stubDetId").ifPresent(mainLog::stubDetId);
    reader.longFromKey("planEnum").ifPresent(mainLog::planEnum);
    reader.longFromKey("planFnum").ifPresent(mainLog::planFnum);
    reader.longFromKey("retNum").ifPresent(mainLog::retNum);
    reader.longFromKey("retPrevId").ifPresent(mainLog::retPrevId);
    reader.stringFromKey("measure").ifPresent(mainLog::measure);
    reader.stringFromKey("mtlBatch").ifPresent(mainLog::mtlBatch);
    reader.longFromKey("serviceLife").ifPresent(mainLog::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(mainLog::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(mainLog::produceTime);
    reader.timeFromKey("expireTime").ifPresent(mainLog::expireTime);
    reader.stringFromKey("projCode").ifPresent(mainLog::projCode);
    reader.stringFromKey("taskCode").ifPresent(mainLog::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(mainLog::brgnCode);
    reader.stringFromKey("outReason").ifPresent(mainLog::outReason);
    reader.stringFromKey("creatorNC").ifPresent(mainLog::creatorNC);
    reader.timeFromKey("createDate").ifPresent(mainLog::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(mainLog::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(mainLog::finishDate);
    reader.stringFromKey("note").ifPresent(mainLog::note);

    return mainLog;
  }

  public static MainLog consMainLogForIn(MainLog mainLog, MainIn mainIn) {
    mainLog.updateUser(mainIn.updateUser());
    mainLog.logStatus(MainLogStatusEnum.Enum1_YSX.symbol());

    String logCate = null;
    switch (MainInCateEnum.of(mainIn.inCate())) {
      case Enum1_RK:
        logCate = MainLogCateEnum.Enum01_RK.symbol();
        break;
      case Enum2_GH:
        logCate = MainLogCateEnum.Enum02_GHRK.symbol();
        break;
      case Enum3_YK:
        logCate = MainLogCateEnum.Enum03_YK.symbol();
        break;
      case Enum9_QT:
        logCate = MainLogCateEnum.Enum09_YK.symbol();
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的入库类型: {0}", mainIn.inCate());
    }
    mainLog.logCate(logCate);

    mainLog.mainId(mainIn.mainId());
    mainLog.inId(mainIn.id());
    mainLog.measure(mainIn.measure());
    mainLog.mtlBatch(mainIn.mtlBatch());
    mainLog.serviceLife(mainIn.serviceLife());
    mainLog.produceTime(mainIn.produceTime());
    mainLog.expireTime(mainIn.expireTime());
    mainLog.creatorNC(mainIn.inNC());
    mainLog.createDate(mainIn.inDate());
    mainLog.planEnum(mainIn.mainPlnNum());
    mainLog.planFnum(mainIn.mainStkNum());
    return mainLog;
  }

  public static MainLog consMainLogForOut(MainLog mainLog, MainOut mainOut) {
    mainLog.updateUser(mainOut.updateUser());
    mainLog.logStatus(MainLogStatusEnum.Enum1_YSX.symbol());

    MainLogCateEnum logCate = MainLogCateEnum.Unknown;
    switch (MainOutCateEnum.of(mainOut.outCate())) {
      case Enum1_ZDLY:
        logCate = MainLogCateEnum.Enum11_ZDLY;
        break;
      case Enum2_LXLY:
        logCate = MainLogCateEnum.Enum12_LXLY;
        break;
      case Enum3_BF:
        logCate = MainLogCateEnum.Enum15_BF;
        break;
      case Enum4_YK:
        logCate = MainLogCateEnum.Enum03_YK;
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类别: {0}", mainOut.outCate());
    }
    mainLog.logCate(logCate.symbol());

    MainLogTypeEnum logType = MainLogTypeEnum.Unknown;
    switch (MainOutTypeEnum.of(mainOut.outType())) {
      case Enum1_ZDCK:
        logType = MainLogTypeEnum.Enum1_ZDCK;
        break;
      case Enum2_ZJCK:
        logType = MainLogTypeEnum.Enum2_ZJCK;
        break;
      case Enum3_BFCK:
        logType = MainLogTypeEnum.Enum3_BFCK;
        break;
      case Enum4_YKCK:
        logType = MainLogTypeEnum.Enum4_YKCK;
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类型: {0}", mainOut.outCate());
    }
    mainLog.logType(logType.symbol());

    mainLog.logFlag(mainOut.outFlag());
    mainLog.mainId(mainOut.mainId());
    mainLog.inId(mainOut.inId());
    mainLog.measure(mainOut.measure());
    mainLog.mtlBatch(mainOut.mtlBatch());
    mainLog.serviceLife(mainOut.serviceLife());
    mainLog.serviceLife2(mainOut.serviceLife2());
    mainLog.produceTime(mainOut.produceTime());
    mainLog.expireTime(mainOut.expireTime());
    mainLog.creatorNC(mainOut.creatorNC());
    mainLog.createDate(mainOut.createDate());
    mainLog.planEnum(mainOut.planEnum());
    mainLog.planFnum(mainOut.planFnum());
    mainLog.outReason(mainOut.outReason());
    mainLog.stubId(mainOut.stubId());
    mainLog.stubDetId(mainOut.stubDetId());
    mainLog.finishDate(mainOut.finishDate());
    mainLog.finishOperNC(mainOut.finishOperNC());
    mainLog.note(mainOut.note());
    mainLog.taskCode(mainOut.taskCode());
    mainLog.brgnCode(mainOut.brgnCode());
    mainLog.projCode(mainOut.projCode());
    return mainLog;
  }

  private MainLogHelper() {}
}
