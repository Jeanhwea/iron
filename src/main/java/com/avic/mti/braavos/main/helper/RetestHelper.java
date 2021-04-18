package com.avic.mti.iron.main.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.main.domain.entity.Retest;
import java.util.Map;

public class RetestHelper {

  public static Retest assignRetest(Retest retest, Map<String, Object> params) {
    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(retest::updateUser);

    reader.stringFromKey("retestCode").ifPresent(retest::retestCode);
    reader.stringFromKey("retestDeptCode").ifPresent(retest::retestDeptCode);
    reader.stringFromKey("retestDeptName").ifPresent(retest::retestDeptName);
    reader.stringFromKey("retester").ifPresent(retest::retester);
    reader.timeFromKey("retestTime").ifPresent(retest::retestTime);
    reader.stringFromKey("creatorNC").ifPresent(retest::creatorNC);
    reader.timeFromKey("createDate").ifPresent(retest::createDate);
    reader.longFromKey("retestFilePage").ifPresent(retest::retestFilePage);
    // reader.stringFromKey("retestFilePath").ifPresent(retest::retestFilePath);
    reader.stringFromKey("note").ifPresent(retest::note);
    reader.stringFromKey("sheetCode").ifPresent(retest::sheetCode);
    reader.stringFromKey("resultText").ifPresent(retest::resultText);
    reader.stringFromKey("retestStandard").ifPresent(retest::retestStandard);
    reader.longFromKey("retestNum01").ifPresent(retest::retestNum01);
    reader.stringFromKey("retestUom01").ifPresent(retest::retestUom01);
    reader.longFromKey("retestNum02").ifPresent(retest::retestNum02);
    reader.stringFromKey("retestUom02").ifPresent(retest::retestUom02);
    reader.longFromKey("var01Num").ifPresent(retest::var01Num);
    reader.longFromKey("var02Num").ifPresent(retest::var02Num);
    reader.longFromKey("var03Num").ifPresent(retest::var03Num);
    reader.longFromKey("var04Num").ifPresent(retest::var04Num);
    reader.longFromKey("var05Num").ifPresent(retest::var05Num);
    reader.stringFromKey("var01Str").ifPresent(retest::var01Str);
    reader.stringFromKey("var02Str").ifPresent(retest::var02Str);
    reader.stringFromKey("var03Str").ifPresent(retest::var03Str);
    reader.stringFromKey("var04Str").ifPresent(retest::var04Str);
    reader.stringFromKey("var05Str").ifPresent(retest::var05Str);
    reader.stringFromKey("retestItemJson").ifPresent(retest::retestItemJson);

    return retest;
  }

  private RetestHelper() {}
}
