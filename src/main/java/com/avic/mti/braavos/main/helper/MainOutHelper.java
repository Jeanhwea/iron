package com.avic.mti.iron.main.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.main.domain.entity.MainOut;
import java.util.Map;

public class MainOutHelper {

  public static MainOut assignMainOut(MainOut mainOut, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(mainOut::updateUser);

    reader.stringFromKey("outStatus").ifPresent(mainOut::outStatus);
    reader.stringFromKey("outCate").ifPresent(mainOut::outCate);
    reader.stringFromKey("outType").ifPresent(mainOut::outType);
    reader.stringFromKey("outFlag").ifPresent(mainOut::outFlag);
    reader.longFromKey("stubId").ifPresent(mainOut::stubId);
    reader.longFromKey("stubDetId").ifPresent(mainOut::stubDetId);
    reader.longFromKey("planEnum").ifPresent(mainOut::planEnum);
    reader.longFromKey("planFnum").ifPresent(mainOut::planFnum);
    reader.stringFromKey("measure").ifPresent(mainOut::measure);
    reader.stringFromKey("mtlBatch").ifPresent(mainOut::mtlBatch);
    reader.longFromKey("inId").ifPresent(mainOut::inId);
    reader.longFromKey("mainId").ifPresent(mainOut::mainId);
    reader.longFromKey("serviceLife").ifPresent(mainOut::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(mainOut::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(mainOut::produceTime);
    reader.timeFromKey("expireTime").ifPresent(mainOut::expireTime);
    reader.stringFromKey("projCode").ifPresent(mainOut::projCode);
    reader.stringFromKey("taskCode").ifPresent(mainOut::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(mainOut::brgnCode);
    reader.stringFromKey("outReason").ifPresent(mainOut::outReason);
    reader.stringFromKey("creatorNC").ifPresent(mainOut::creatorNC);
    reader.timeFromKey("createDate").ifPresent(mainOut::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(mainOut::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(mainOut::finishDate);
    reader.stringFromKey("note").ifPresent(mainOut::note);
    reader.stringFromKey("shelfJson").ifPresent(mainOut::shelfJson);

    return mainOut;
  }

  private MainOutHelper() {}
}
