package com.avic.mti.iron.waste.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.waste.domain.entity.WprdOut;
import java.util.Map;

public class WprdOutHelper {

  public static WprdOut assignWprdOut(WprdOut wprdOut, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(wprdOut::updateUser);

    reader.stringFromKey("outStatus").ifPresent(wprdOut::outStatus);
    reader.stringFromKey("outCate").ifPresent(wprdOut::outCate);
    reader.stringFromKey("outType").ifPresent(wprdOut::outType);
    reader.stringFromKey("outFlag").ifPresent(wprdOut::outFlag);
    reader.longFromKey("stubId").ifPresent(wprdOut::stubId);
    reader.longFromKey("stubDetId").ifPresent(wprdOut::stubDetId);
    reader.longFromKey("planEnum").ifPresent(wprdOut::planEnum);
    reader.longFromKey("planFnum").ifPresent(wprdOut::planFnum);
    reader.stringFromKey("measure").ifPresent(wprdOut::measure);
    reader.stringFromKey("mtlBatch").ifPresent(wprdOut::mtlBatch);
    reader.longFromKey("inId").ifPresent(wprdOut::inId);
    reader.longFromKey("wprdId").ifPresent(wprdOut::wprdId);
    reader.stringFromKey("wprdCate").ifPresent(wprdOut::wprdCate);
    reader.stringFromKey("wprdType").ifPresent(wprdOut::wprdType);
    reader.longFromKey("serviceLife").ifPresent(wprdOut::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(wprdOut::serviceLife2);
    reader.timeFromKey("produceTime").ifPresent(wprdOut::produceTime);
    reader.timeFromKey("expireTime").ifPresent(wprdOut::expireTime);
    reader.stringFromKey("projCode").ifPresent(wprdOut::projCode);
    reader.stringFromKey("taskCode").ifPresent(wprdOut::taskCode);
    reader.stringFromKey("brgnCode").ifPresent(wprdOut::brgnCode);
    reader.stringFromKey("outReason").ifPresent(wprdOut::outReason);
    reader.stringFromKey("creatorNC").ifPresent(wprdOut::creatorNC);
    reader.timeFromKey("createDate").ifPresent(wprdOut::createDate);
    reader.stringFromKey("finishOperNC").ifPresent(wprdOut::finishOperNC);
    reader.timeFromKey("finishDate").ifPresent(wprdOut::finishDate);
    reader.stringFromKey("note").ifPresent(wprdOut::note);
    reader.stringFromKey("shelfText").ifPresent(wprdOut::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(wprdOut::shelfJson);

    return wprdOut;
  }

  private WprdOutHelper() {}
}
