package com.avic.mti.iron.mould.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.mould.domain.entity.Mould;
import java.util.Map;

public class MouldHelper {

  public MouldHelper() {}

  public static Mould assignMould(Mould mould, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(mould::updateUser);

    reader.stringFromKey("moldCode").ifPresent(mould::moldCode);
    reader.stringFromKey("moldName").ifPresent(mould::moldName);
    reader.stringFromKey("moldCate").ifPresent(mould::moldCate);
    reader.stringFromKey("moldSpec").ifPresent(mould::moldSpec);
    reader.stringFromKey("moldDept").ifPresent(mould::moldDept);
    reader.stringFromKey("moldAdmin").ifPresent(mould::moldAdmin);
    reader.stringFromKey("moldMaker").ifPresent(mould::moldMaker);
    reader.stringFromKey("moldStatus").ifPresent(mould::moldStatus);
    reader.stringFromKey("blueprintNo").ifPresent(mould::blueprintNo);
    reader.timeFromKey("serviceLife").ifPresent(mould::serviceLife);
    reader.stringFromKey("moldSize").ifPresent(mould::moldSize);
    reader.stringFromKey("inType").ifPresent(mould::inType);
    reader.stringFromKey("inStub").ifPresent(mould::inStub);
    reader.stringFromKey("inNC").ifPresent(mould::inNC);
    reader.timeFromKey("inDate").ifPresent(mould::inDate);
    reader.stringFromKey("note").ifPresent(mould::note);
    reader.stringFromKey("shelfJson").ifPresent(mould::shelfJson);

    return mould;
  }
}
