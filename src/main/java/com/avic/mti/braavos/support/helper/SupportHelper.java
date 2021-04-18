package com.avic.mti.iron.support.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.support.domain.entity.Support;
import java.util.Map;

public class SupportHelper {

  public static Support assignSupport(Support support, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(support::updateUser);
    reader.stringFromKey("suptCode").ifPresent(support::suptCode);
    reader.stringFromKey("suptName").ifPresent(support::suptName);
    reader.stringFromKey("suptCate").ifPresent(support::suptCate);
    reader.stringFromKey("suptSpec").ifPresent(support::suptSpec);
    reader.stringFromKey("suptType").ifPresent(support::suptType);
    reader.stringFromKey("suptDept").ifPresent(support::suptDept);
    reader.stringFromKey("suptUsage").ifPresent(support::suptUsage);
    reader.stringFromKey("suptMaker").ifPresent(support::suptMaker);
    reader.stringFromKey("measure").ifPresent(support::measure);
    reader.stringFromKey("mtlMark").ifPresent(support::mtlMark);
    reader.stringFromKey("inType").ifPresent(support::inType);
    reader.stringFromKey("inStub").ifPresent(support::inStub);
    reader.stringFromKey("inNC").ifPresent(support::inNC);
    reader.timeFromKey("inDate").ifPresent(support::inDate);
    reader.stringFromKey("note").ifPresent(support::note);
    reader.stringFromKey("shelfJson").ifPresent(support::shelfJson);
    return support;
  }

  private SupportHelper() {}
}
