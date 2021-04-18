package com.avic.mti.iron.tool.helper;

import com.avic.mti.iron.common.http.request.ParamReader;
import com.avic.mti.iron.tool.domain.entity.ToolMaterial;
import java.util.Map;

public class ToolMaterialHelper {

  public static ToolMaterial assignToolMaterial(
      ToolMaterial toolMaterial, Map<String, Object> params) {

    ParamReader reader = ParamReader.init(params);
    reader.stringFromKey("updateUser").ifPresent(toolMaterial::updateUser);

    reader.stringFromKey("toolCode").ifPresent(toolMaterial::toolCode);
    reader.stringFromKey("toolName").ifPresent(toolMaterial::toolName);
    reader.stringFromKey("toolCate").ifPresent(toolMaterial::toolCate);
    reader.stringFromKey("toolSpec").ifPresent(toolMaterial::toolSpec);
    reader.stringFromKey("toolType").ifPresent(toolMaterial::toolType);
    reader.stringFromKey("toolMaker").ifPresent(toolMaterial::toolMaker);
    reader.stringFromKey("toolDept").ifPresent(toolMaterial::toolDept);
    reader.stringFromKey("mtlMark").ifPresent(toolMaterial::mtlMark);
    reader.stringFromKey("mtlStandard").ifPresent(toolMaterial::mtlStandard);
    reader.stringFromKey("measure").ifPresent(toolMaterial::measure);
    reader.stringFromKey("inType").ifPresent(toolMaterial::inType);
    reader.stringFromKey("inStub").ifPresent(toolMaterial::inStub);
    reader.stringFromKey("inNC").ifPresent(toolMaterial::inNC);
    reader.timeFromKey("inDate").ifPresent(toolMaterial::inDate);
    reader.longFromKey("minStkNum").ifPresent(toolMaterial::minStkNum);
    reader.longFromKey("serviceLife").ifPresent(toolMaterial::serviceLife);
    reader.longFromKey("serviceLife2").ifPresent(toolMaterial::serviceLife2);
    reader.stringFromKey("note").ifPresent(toolMaterial::note);
    reader.stringFromKey("shelfText").ifPresent(toolMaterial::shelfText);
    reader.stringFromKey("shelfJson").ifPresent(toolMaterial::shelfJson);

    return toolMaterial;
  }

  private ToolMaterialHelper() {}
}
