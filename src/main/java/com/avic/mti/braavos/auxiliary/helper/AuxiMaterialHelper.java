package com.avic.mti.iron.auxiliary.helper;

import com.avic.mti.iron.auxiliary.domain.entity.AuxiMaterial;
import com.avic.mti.iron.common.http.request.ParamReader;
import java.util.Map;

public class AuxiMaterialHelper {

  public static AuxiMaterial assignAuxiMaterial(
      AuxiMaterial auxiMaterial, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(auxiMaterial::updateUser);

    reader.stringFromKey("auxiCode").ifPresent(auxiMaterial::auxiCode);
    reader.stringFromKey("auxiName").ifPresent(auxiMaterial::auxiName);
    reader.stringFromKey("auxiCate").ifPresent(auxiMaterial::auxiCate);
    reader.stringFromKey("auxiSpec").ifPresent(auxiMaterial::auxiSpec);
    reader.stringFromKey("auxiType").ifPresent(auxiMaterial::auxiType);
    reader.stringFromKey("auxiMaker").ifPresent(auxiMaterial::auxiMaker);
    reader.stringFromKey("auxiDept").ifPresent(auxiMaterial::auxiDept);
    reader.stringFromKey("mtlMark").ifPresent(auxiMaterial::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(auxiMaterial::mtlStandard);
    reader.stringFromKey("measure").ifPresent(auxiMaterial::measure);
    reader.stringFromKey("inType").ifPresent(auxiMaterial::inType);
    reader.stringFromKey("inStub").ifPresent(auxiMaterial::inStub);
    reader.stringFromKey("inNC").ifPresent(auxiMaterial::inNC);
    reader.timeFromKey("inDate").ifPresent(auxiMaterial::inDate);
    reader.stringFromKey("note").ifPresent(auxiMaterial::note);
    reader.stringFromKey("shelfJson").ifPresent(auxiMaterial::shelfJson);

    return auxiMaterial;
  }

  private AuxiMaterialHelper() {}
}
