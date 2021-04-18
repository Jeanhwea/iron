package com.avic.mti.iron.measure.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.measure.domain.entity.MeasOut;
import java.util.Map;

public class MeasOutHelper {

  public static MeasOut assignMeasOut(MeasOut measOut, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(measOut::updateUser);

    reader.stringFromKey("outStatus").ifPresent(measOut::outStatus);
    reader.stringFromKey("outCate").ifPresent(measOut::outCate);
    reader.stringFromKey("outType").ifPresent(measOut::outType);
    reader.stringFromKey("outFlag").ifPresent(measOut::outFlag);
    reader.longFromKey("stubId").ifPresent(measOut::stubId);
    reader.longFromKey("stubDetId").ifPresent(measOut::stubDetId);
    reader.longFromKey("planEnum").ifPresent(measOut::planEnum);
    reader.longFromKey("planFnum").ifPresent(measOut::planFnum);
    reader.stringFromKey("measure").ifPresent(measOut::measure);
    reader.stringFromKey("mtlBatch").ifPresent(measOut::mtlBatch);
    reader.longFromKey("inId").ifPresent(measOut::inId);
    reader.longFromKey("measId").ifPresent(measOut::measId);
    reader.longFromKey("serviceLife").ifPresent(measOut::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(measOut::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(measOut::produceTime);
    reader.timeFromKey("expireTime").ifPresent(measOut::expireTime);
    reader.stringFromKey("projCode").ifPresent(measOut::projCode);
    reader.stringFromKey("taskCode").ifPresent(measOut::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(measOut::brgnCode);
    reader.stringFromKey("outReason").ifPresent(measOut::outReason);
    reader.stringFromKey("creatorNC").ifPresent(measOut::creatorNC);
    reader.timeFromKey("createDate").ifPresent(measOut::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(measOut::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(measOut::finishDate);
    reader.stringFromKey("note").ifPresent(measOut::note);
    reader.stringFromKey("shelfText").ifPresent(measOut::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(measOut::shelfJson);

    return measOut;
  }

  private MeasOutHelper() {}
}
