package com.avic.mti.iron.tool.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.tool.domain.entity.ToolOut;
import java.util.Map;

public class ToolOutHelper {

  public static ToolOut assignToolOut(ToolOut toolOut, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(toolOut::updateUser);

    reader.stringFromKey("outStatus").ifPresent(toolOut::outStatus);
    reader.stringFromKey("outCate").ifPresent(toolOut::outCate);
    reader.stringFromKey("outType").ifPresent(toolOut::outType);
    reader.stringFromKey("outFlag").ifPresent(toolOut::outFlag);
    reader.longFromKey("stubId").ifPresent(toolOut::stubId);
    reader.longFromKey("stubDetId").ifPresent(toolOut::stubDetId);
    reader.longFromKey("planEnum").ifPresent(toolOut::planEnum);
    reader.longFromKey("planFnum").ifPresent(toolOut::planFnum);
    reader.stringFromKey("measure").ifPresent(toolOut::measure);
    reader.stringFromKey("mtlBatch").ifPresent(toolOut::mtlBatch);
    reader.longFromKey("inId").ifPresent(toolOut::inId);
    reader.longFromKey("toolId").ifPresent(toolOut::toolId);
    reader.longFromKey("serviceLife").ifPresent(toolOut::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(toolOut::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(toolOut::produceTime);
    reader.timeFromKey("expireTime").ifPresent(toolOut::expireTime);
    reader.stringFromKey("projCode").ifPresent(toolOut::projCode);
    reader.stringFromKey("taskCode").ifPresent(toolOut::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(toolOut::brgnCode);
    reader.stringFromKey("outReason").ifPresent(toolOut::outReason);
    reader.stringFromKey("creatorNC").ifPresent(toolOut::creatorNC);
    reader.timeFromKey("createDate").ifPresent(toolOut::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(toolOut::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(toolOut::finishDate);
    reader.stringFromKey("note").ifPresent(toolOut::note);
    reader.stringFromKey("shelfText").ifPresent(toolOut::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(toolOut::shelfJson);

    return toolOut;
  }

  private ToolOutHelper() {}
}
