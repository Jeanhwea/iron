package com.avic.mti.iron.measure.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.measure.domain.entity.MeasMaterial;
import java.util.Map;

public class MeasMaterialHelper {

  public static MeasMaterial assignMeasMaterial(
      MeasMaterial measMaterial, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(measMaterial::updateUser);

    reader.stringFromKey("measCode").ifPresent(measMaterial::measCode);
    reader.stringFromKey("measName").ifPresent(measMaterial::measName);
    reader.stringFromKey("measCate").ifPresent(measMaterial::measCate);
    reader.stringFromKey("measSpec").ifPresent(measMaterial::measSpec);
    reader.stringFromKey("measType").ifPresent(measMaterial::measType);
    reader.stringFromKey("measMaker").ifPresent(measMaterial::measMaker);
    reader.stringFromKey("measUsage").ifPresent(measMaterial::measUsage);
    reader.stringFromKey("measDept").ifPresent(measMaterial::measDept);
    reader.stringFromKey("mtlMark").ifPresent(measMaterial::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(measMaterial::mtlStandard);
    reader.stringFromKey("measure").ifPresent(measMaterial::measure);
    reader.stringFromKey("inType").ifPresent(measMaterial::inType);
    reader.stringFromKey("inStub").ifPresent(measMaterial::inStub);
    reader.stringFromKey("inNC").ifPresent(measMaterial::inNC);
    reader.timeFromKey("inDate").ifPresent(measMaterial::inDate);
    reader.longFromKey("minStkNum").ifPresent(measMaterial::minStkNum);
    reader.longFromKey("serviceLife").ifPresent(measMaterial::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(measMaterial::serviceLife2);
    reader.stringFromKey("note").ifPresent(measMaterial::note);
    reader.stringFromKey("shelfText").ifPresent(measMaterial::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(measMaterial::shelfJson);

    return measMaterial;
  }

  private MeasMaterialHelper() {}
}
