package com.avic.mti.iron.waste.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.waste.domain.entity.WprdMaterial;
import java.util.Map;

public class WprdMaterialHelper {

  public static WprdMaterial assignWprdMaterial(
      WprdMaterial wprdMaterial, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(wprdMaterial::updateUser);

    reader.stringFromKey("wprdCode").ifPresent(wprdMaterial::wprdCode);
    reader.stringFromKey("wprdName").ifPresent(wprdMaterial::wprdName);
    reader.stringFromKey("wprdCate").ifPresent(wprdMaterial::wprdCate);
    reader.stringFromKey("wprdSpec").ifPresent(wprdMaterial::wprdSpec);
    reader.stringFromKey("wprdType").ifPresent(wprdMaterial::wprdType);
    reader.stringFromKey("wprdMaker").ifPresent(wprdMaterial::wprdMaker);
    reader.stringFromKey("wprdDept").ifPresent(wprdMaterial::wprdDept);
    reader.stringFromKey("mtlMark").ifPresent(wprdMaterial::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(wprdMaterial::mtlStandard);
    reader.stringFromKey("measure").ifPresent(wprdMaterial::measure);
    reader.stringFromKey("inType").ifPresent(wprdMaterial::inType);
    reader.stringFromKey("inStub").ifPresent(wprdMaterial::inStub);
    reader.stringFromKey("inNC").ifPresent(wprdMaterial::inNC);
    reader.timeFromKey("inDate").ifPresent(wprdMaterial::inDate);
    reader.longFromKey("minStkNum").ifPresent(wprdMaterial::minStkNum);
    reader.longFromKey("serviceLife").ifPresent(wprdMaterial::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(wprdMaterial::serviceLife2);
    reader.stringFromKey("note").ifPresent(wprdMaterial::note);
    reader.stringFromKey("shelfText").ifPresent(wprdMaterial::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(wprdMaterial::shelfJson);

    return wprdMaterial;
  }

  private WprdMaterialHelper() {}
}
