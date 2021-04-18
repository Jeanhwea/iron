package com.avic.mti.iron.tool.helper;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.common.symbol.tool.*;
import com.avic.mti.iron.tool.domain.entity.ToolIn;
import com.avic.mti.iron.tool.domain.entity.ToolLog;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import java.util.Map;

public class ToolLogHelper {

  public static ToolLog assignToolLog(ToolLog toolLog, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(toolLog::updateUser);

    reader.stringFromKey("logStatus").ifPresent(toolLog::logStatus);
    reader.stringFromKey("logCate").ifPresent(toolLog::logCate);
    reader.stringFromKey("logType").ifPresent(toolLog::logType);
    reader.stringFromKey("logFlag").ifPresent(toolLog::logFlag);
    reader.longFromKey("toolId").ifPresent(toolLog::toolId);
    reader.longFromKey("inId").ifPresent(toolLog::inId);
    reader.longFromKey("stubId").ifPresent(toolLog::stubId);
    reader.longFromKey("stubDetId").ifPresent(toolLog::stubDetId);
    reader.longFromKey("planEnum").ifPresent(toolLog::planEnum);
    reader.longFromKey("planFnum").ifPresent(toolLog::planFnum);
    reader.longFromKey("retNum").ifPresent(toolLog::retNum);
    reader.longFromKey("retPrevId").ifPresent(toolLog::retPrevId);
    reader.stringFromKey("measure").ifPresent(toolLog::measure);
    reader.stringFromKey("mtlBatch").ifPresent(toolLog::mtlBatch);
    reader.longFromKey("serviceLife").ifPresent(toolLog::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(toolLog::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(toolLog::produceTime);
    reader.timeFromKey("expireTime").ifPresent(toolLog::expireTime);
    reader.stringFromKey("projCode").ifPresent(toolLog::projCode);
    reader.stringFromKey("taskCode").ifPresent(toolLog::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(toolLog::brgnCode);
    reader.stringFromKey("outReason").ifPresent(toolLog::outReason);
    reader.stringFromKey("creatorNC").ifPresent(toolLog::creatorNC);
    reader.timeFromKey("createDate").ifPresent(toolLog::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(toolLog::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(toolLog::finishDate);
    reader.stringFromKey("note").ifPresent(toolLog::note);

    return toolLog;
  }

  public static ToolLog consToolLogForIn(ToolLog toolLog, ToolIn toolIn) {
    toolLog.updateUser(toolIn.updateUser());
    toolLog.logStatus(ToolLogStatusEnum.Enum1_YSX.symbol());

    String logCate = null;
    switch (ToolInCateEnum.of(toolIn.inCate())) {
      case Enum1_RK:
        logCate = ToolLogCateEnum.Enum01_RK.symbol();
        break;
      case Enum2_GH:
        logCate = ToolLogCateEnum.Enum02_GHRK.symbol();
        break;
      case Enum3_YK:
        logCate = ToolLogCateEnum.Enum03_YK.symbol();
        break;
      case Enum9_QT:
        logCate = ToolLogCateEnum.Enum09_YK.symbol();
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的入库类型: {0}", toolIn.inCate());
    }
    toolLog.logCate(logCate);

    toolLog.toolId(toolIn.toolId());
    toolLog.inId(toolIn.id());
    toolLog.measure(toolIn.measure());
    toolLog.mtlBatch(toolIn.mtlBatch());
    toolLog.serviceLife(toolIn.serviceLife());
    toolLog.produceTime(toolIn.produceTime());
    toolLog.expireTime(toolIn.expireTime());
    toolLog.creatorNC(toolIn.inNC());
    toolLog.createDate(toolIn.inDate());
    toolLog.planEnum(toolIn.toolPlnNum());
    toolLog.planFnum(toolIn.toolStkNum());
    return toolLog;
  }

  public static ToolLog consToolLogForOut(ToolLog toolLog, ToolOut toolOut) {
    toolLog.updateUser(toolOut.updateUser());
    toolLog.logStatus(ToolLogStatusEnum.Enum1_YSX.symbol());

    String logCate = null;
    switch (ToolOutCateEnum.of(toolOut.outCate())) {
      case Enum1_JY:
        logCate = ToolLogCateEnum.Enum11_JY.symbol();
        break;
      case Enum2_LY:
        logCate = ToolLogCateEnum.Enum12_LY.symbol();
        break;
      case Enum3_LXJY:
        logCate = ToolLogCateEnum.Enum13_LXJY.symbol();
        break;
      case Enum4_LXLY:
        logCate = ToolLogCateEnum.Enum14_LXLY.symbol();
        break;
      case Enum5_BF:
        logCate = ToolLogCateEnum.Enum15_BF.symbol();
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类型: {0}", toolOut.outCate());
    }
    toolLog.logCate(logCate);

    ToolLogTypeEnum logType = ToolLogTypeEnum.Unknown;
    switch (ToolOutTypeEnum.of(toolOut.outType())) {
      case Enum1_ZDCK:
        logType = ToolLogTypeEnum.Enum1_ZDCK;
        break;
      case Enum2_ZJCK:
        logType = ToolLogTypeEnum.Enum2_ZJCK;
        break;
      case Enum3_BFCK:
        logType = ToolLogTypeEnum.Enum3_BFCK;
        break;
      case Enum4_YKCK:
        logType = ToolLogTypeEnum.Enum4_YKCK;
        break;
      case Unknown:
      default:
        throw new BadRequestException("无法解析的出库类型: {0}", toolOut.outCate());
    }
    toolLog.logType(logType.symbol());

    toolLog.logFlag(toolOut.outFlag());
    toolLog.toolId(toolOut.toolId());
    toolLog.inId(toolOut.inId());
    toolLog.measure(toolOut.measure());
    toolLog.mtlBatch(toolOut.mtlBatch());
    toolLog.serviceLife(toolOut.serviceLife());
    toolLog.serviceLife2(toolOut.serviceLife2());
    toolLog.produceTime(toolOut.produceTime());
    toolLog.expireTime(toolOut.expireTime());
    toolLog.creatorNC(toolOut.creatorNC());
    toolLog.createDate(toolOut.createDate());
    toolLog.planEnum(toolOut.planEnum());
    toolLog.planFnum(toolOut.planFnum());
    toolLog.outReason(toolOut.outReason());
    toolLog.stubId(toolOut.stubId());
    toolLog.stubDetId(toolOut.stubDetId());
    toolLog.finishDate(toolOut.finishDate());
    toolLog.finishOperNC(toolOut.finishOperNC());
    toolLog.note(toolOut.note());
    toolLog.taskCode(toolOut.taskCode());
    toolLog.brgnCode(toolOut.brgnCode());
    toolLog.projCode(toolOut.projCode());
    return toolLog;
  }

  private ToolLogHelper() {}
}
