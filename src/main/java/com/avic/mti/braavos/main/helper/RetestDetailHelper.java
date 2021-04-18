package com.avic.mti.iron.main.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.main.domain.entity.RetestDetail;
import java.util.Map;

public class RetestDetailHelper {

  public static RetestDetail assignRetestDetail(
      RetestDetail retestDetail, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(retestDetail::updateUser);

    reader.longFromKey("retestId").ifPresent(retestDetail::retestId);
    reader.longFromKey("inId").ifPresent(retestDetail::inId);
    reader.stringFromKey("inCode").ifPresent(retestDetail::inCode);
    reader.longFromKey("mainId").ifPresent(retestDetail::mainId);
    reader.stringFromKey("mainCode").ifPresent(retestDetail::mainCode);
    reader.stringFromKey("mtlBatch").ifPresent(retestDetail::mtlBatch);
    reader.stringFromKey("mainType").ifPresent(retestDetail::mainType);
    reader.stringFromKey("mainSpec").ifPresent(retestDetail::mainSpec);
    reader.stringFromKey("mtlMark").ifPresent(retestDetail::mtlMark);
    reader.stringFromKey("smplCode").ifPresent(retestDetail::smplCode);
    reader.stringFromKey("smplName").ifPresent(retestDetail::smplName);
    reader.stringFromKey("retestStatus").ifPresent(retestDetail::retestStatus);
    reader.longFromKey("retestNum01").ifPresent(retestDetail::retestNum01);
    reader.stringFromKey("retestUom01").ifPresent(retestDetail::retestUom01);
    reader.longFromKey("retestNum02").ifPresent(retestDetail::retestNum02);
    reader.stringFromKey("retestUom02").ifPresent(retestDetail::retestUom02);
    reader.longFromKey("retestVar01Num").ifPresent(retestDetail::retestVar01Num);
    reader.longFromKey("retestVar02Num").ifPresent(retestDetail::retestVar02Num);
    reader.longFromKey("retestVar03Num").ifPresent(retestDetail::retestVar03Num);
    reader.longFromKey("retestVar04Num").ifPresent(retestDetail::retestVar04Num);
    reader.longFromKey("retestVar05Num").ifPresent(retestDetail::retestVar05Num);
    reader.stringFromKey("retestVar01Str").ifPresent(retestDetail::retestVar01Str);
    reader.stringFromKey("retestVar02Str").ifPresent(retestDetail::retestVar02Str);
    reader.stringFromKey("retestVar03Str").ifPresent(retestDetail::retestVar03Str);
    reader.stringFromKey("retestVar04Str").ifPresent(retestDetail::retestVar04Str);
    reader.stringFromKey("retestVar05Str").ifPresent(retestDetail::retestVar05Str);
    reader.stringFromKey("note").ifPresent(retestDetail::note);
    reader.stringFromKey("creatorNC").ifPresent(retestDetail::creatorNC);
    reader.timeFromKey("createDate").ifPresent(retestDetail::createDate);
    return retestDetail;
  }

  private RetestDetailHelper() {}
}
