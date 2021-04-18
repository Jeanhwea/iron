package com.avic.mti.iron.measure.helper;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.measure.*;
import com.avic.mti.iron.measure.domain.entity.MeasIn;
import com.avic.mti.iron.measure.domain.entity.MeasLog;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import java.util.Map;

public class MeasLogHelper {

  public static MeasLog assignMeasLog(MeasLog measLog, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(measLog::updateUser);

    reader.stringFromKey("logStatus").ifPresent(measLog::logStatus);
    reader.stringFromKey("logCate").ifPresent(measLog::logCate);
    reader.stringFromKey("logType").ifPresent(measLog::logType);
    reader.stringFromKey("logFlag").ifPresent(measLog::logFlag);
    reader.longFromKey("measId").ifPresent(measLog::measId);
    reader.longFromKey("inId").ifPresent(measLog::inId);
    reader.longFromKey("stubId").ifPresent(measLog::stubId);
    reader.longFromKey("stubDetId").ifPresent(measLog::stubDetId);
    reader.longFromKey("planEnum").ifPresent(measLog::planEnum);
    reader.longFromKey("planFnum").ifPresent(measLog::planFnum);
    reader.longFromKey("retNum").ifPresent(measLog::retNum);
    reader.longFromKey("retPrevId").ifPresent(measLog::retPrevId);
    reader.stringFromKey("measure").ifPresent(measLog::measure);
    reader.stringFromKey("mtlBatch").ifPresent(measLog::mtlBatch);
    reader.longFromKey("serviceLife").ifPresent(measLog::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(measLog::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(measLog::produceTime);
    reader.timeFromKey("expireTime").ifPresent(measLog::expireTime);
    reader.stringFromKey("projCode").ifPresent(measLog::projCode);
    reader.stringFromKey("taskCode").ifPresent(measLog::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(measLog::brgnCode);
    reader.stringFromKey("outReason").ifPresent(measLog::outReason);
    reader.stringFromKey("creatorNC").ifPresent(measLog::creatorNC);
    reader.timeFromKey("createDate").ifPresent(measLog::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(measLog::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(measLog::finishDate);
    reader.stringFromKey("note").ifPresent(measLog::note);

    return measLog;
  }

  public static MeasLog consMeasLogForIn(MeasLog measLog, MeasIn measIn) {
    measLog.updateUser(measIn.updateUser());
    measLog.logStatus(MeasLogStatusEnum.Enum1_YSX.symbol());

    String logCate = null;
    switch (MeasInCateEnum.of(measIn.inCate())) {
      case Enum1_RK:
        logCate = MeasLogCateEnum.Enum01_RK.symbol();
        break;
      case Enum2_GH:
        logCate = MeasLogCateEnum.Enum02_GHRK.symbol();
        break;
      case Enum3_YK:
        logCate = MeasLogCateEnum.Enum03_YK.symbol();
        break;
      case Enum9_QT:
        logCate = MeasLogCateEnum.Enum09_YK.symbol();
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的入库类型: {0}", measIn.inCate());
    }
    measLog.logCate(logCate);

    measLog.measId(measIn.measId());
    measLog.inId(measIn.id());
    measLog.measure(measIn.measure());
    measLog.mtlBatch(measIn.mtlBatch());
    measLog.serviceLife(measIn.serviceLife());
    measLog.produceTime(measIn.produceTime());
    measLog.expireTime(measIn.expireTime());
    measLog.creatorNC(measIn.inNC());
    measLog.createDate(measIn.inDate());
    measLog.planEnum(measIn.measPlnNum());
    measLog.planFnum(measIn.measStkNum());
    return measLog;
  }

  public static MeasLog consMeasLogForOut(MeasLog measLog, MeasOut measOut) {
    measLog.updateUser(measOut.updateUser());
    measLog.logStatus(MeasLogStatusEnum.Enum1_YSX.symbol());

    String logCate = null;
    switch (MeasOutCateEnum.of(measOut.outCate())) {
      case Enum1_JY:
        logCate = MeasLogCateEnum.Enum11_JY.symbol();
        break;
      case Enum2_LY:
        logCate = MeasLogCateEnum.Enum12_LY.symbol();
        break;
      case Enum3_LXJY:
        logCate = MeasLogCateEnum.Enum13_LXJY.symbol();
        break;
      case Enum4_LXLY:
        logCate = MeasLogCateEnum.Enum14_LXLY.symbol();
        break;
      case Enum5_BF:
        logCate = MeasLogCateEnum.Enum15_BF.symbol();
        break;
      case Enum6_SJ:
        logCate = MeasLogCateEnum.Enum16_SJ.symbol();
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类型: {0}", measOut.outCate());
    }
    measLog.logCate(logCate);

    MeasLogTypeEnum logType = MeasLogTypeEnum.Unknown;
    switch (MeasOutTypeEnum.of(measOut.outType())) {
      case Enum1_ZDCK:
        logType = MeasLogTypeEnum.Enum1_ZDCK;
        break;
      case Enum2_ZJCK:
        logType = MeasLogTypeEnum.Enum2_ZJCK;
        break;
      case Enum3_BFCK:
        logType = MeasLogTypeEnum.Enum3_BFCK;
        break;
      case Enum4_YKCK:
        logType = MeasLogTypeEnum.Enum4_YKCK;
        break;
      case Enum5_CJCK:
        logType = MeasLogTypeEnum.Enum5_SJCK;
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类型: {0}", measOut.outCate());
    }
    measLog.logType(logType.symbol());

    measLog.logFlag(measOut.outFlag());
    measLog.measId(measOut.measId());
    measLog.inId(measOut.inId());
    measLog.measure(measOut.measure());
    measLog.mtlBatch(measOut.mtlBatch());
    measLog.serviceLife(measOut.serviceLife());
    measLog.serviceLife2(measOut.serviceLife2());
    measLog.produceTime(measOut.produceTime());
    measLog.expireTime(measOut.expireTime());
    measLog.creatorNC(measOut.creatorNC());
    measLog.createDate(measOut.createDate());
    measLog.planEnum(measOut.planEnum());
    measLog.planFnum(measOut.planFnum());
    measLog.outReason(measOut.outReason());
    measLog.stubId(measOut.stubId());
    measLog.stubDetId(measOut.stubDetId());
    measLog.finishDate(measOut.finishDate());
    measLog.finishOperNC(measOut.finishOperNC());
    measLog.note(measOut.note());
    measLog.taskCode(measOut.taskCode());
    measLog.brgnCode(measOut.brgnCode());
    measLog.projCode(measOut.projCode());
    return measLog;
  }

  private MeasLogHelper() {}
}
