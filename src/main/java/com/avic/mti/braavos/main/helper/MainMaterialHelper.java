package com.avic.mti.iron.main.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.main.domain.entity.MainMaterial;
import java.util.Map;

public class MainMaterialHelper {

  public static MainMaterial assignMainMaterial(
      MainMaterial mainMaterial, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(mainMaterial::updateUser);

    reader.stringFromKey("mainCode").ifPresent(mainMaterial::mainCode);
    reader.stringFromKey("mainName").ifPresent(mainMaterial::mainName);
    reader.stringFromKey("mainCate").ifPresent(mainMaterial::mainCate);
    reader.stringFromKey("mainSpec").ifPresent(mainMaterial::mainSpec);
    reader.stringFromKey("mainType").ifPresent(mainMaterial::mainType);
    reader.stringFromKey("mainMaker").ifPresent(mainMaterial::mainMaker);
    reader.stringFromKey("mainDept").ifPresent(mainMaterial::mainDept);
    reader.stringFromKey("mtlMark").ifPresent(mainMaterial::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(mainMaterial::mtlStandard);
    reader.stringFromKey("measure").ifPresent(mainMaterial::measure);
    reader.stringFromKey("inType").ifPresent(mainMaterial::inType);
    reader.stringFromKey("inStub").ifPresent(mainMaterial::inStub);
    reader.stringFromKey("inNC").ifPresent(mainMaterial::inNC);
    reader.timeFromKey("inDate").ifPresent(mainMaterial::inDate);
    reader.longFromKey("minStkNum").ifPresent(mainMaterial::minStkNum);
    reader.longFromKey("serviceLife").ifPresent(mainMaterial::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(mainMaterial::serviceLife2);
    reader.stringFromKey("note").ifPresent(mainMaterial::note);
    reader.stringFromKey("shelfJson").ifPresent(mainMaterial::shelfJson);

    return mainMaterial;
  }

  private MainMaterialHelper() {}
}
