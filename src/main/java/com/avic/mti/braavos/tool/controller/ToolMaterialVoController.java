package com.avic.mti.iron.tool.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.tool.domain.entity.ToolMaterialVo;
import com.avic.mti.iron.tool.service.ToolMaterialVoService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tools/view")
public class ToolMaterialVoController {

  public static final Logger logger = LoggerFactory.getLogger(ToolMaterialVoController.class);

  private final ToolMaterialVoService toolMaterialVoService;

  @Autowired
  public ToolMaterialVoController(ToolMaterialVoService toolMaterialVoService) {
    this.toolMaterialVoService = toolMaterialVoService;
  }

  @GetMapping("")
  public Page<ToolMaterialVo> getToolMaterialVosPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("toolCode", FieldTypeEnum.String)
            .put("toolName", FieldTypeEnum.String)
            .put("toolCate", FieldTypeEnum.Enumeration)
            .put("toolSpec", FieldTypeEnum.String)
            .put("toolType", FieldTypeEnum.Enumeration)
            .put("toolMaker", FieldTypeEnum.String)
            .put("toolDept", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("minStkNum", FieldTypeEnum.Long)
            .put("note", FieldTypeEnum.String)
            .put("totalStkNum", FieldTypeEnum.Long)
            .put("totalAvlNum", FieldTypeEnum.Long)
            .put("expiredCount", FieldTypeEnum.Long)
            .put("totalPlanEnum", FieldTypeEnum.Long)
            .put("totalPlanFnum", FieldTypeEnum.Long)
            .fields();
    return this.toolMaterialVoService.findToolMaterialVos(params, fields);
  }

  @GetMapping("/list")
  public List<ToolMaterialVo> getToolMaterialVosList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("toolCode", FieldTypeEnum.String)
            .put("toolName", FieldTypeEnum.String)
            .put("toolCate", FieldTypeEnum.Enumeration)
            .put("toolSpec", FieldTypeEnum.String)
            .put("toolType", FieldTypeEnum.Enumeration)
            .put("toolMaker", FieldTypeEnum.String)
            .put("toolDept", FieldTypeEnum.String)
            .put("mtlMark", FieldTypeEnum.String)
            .put("mtlStandard", FieldTypeEnum.String)
            .put("measure", FieldTypeEnum.String)
            .put("inType", FieldTypeEnum.Enumeration)
            .put("inStub", FieldTypeEnum.String)
            .put("inNC", FieldTypeEnum.String)
            .put("inDate", FieldTypeEnum.Timestamp)
            .put("minStkNum", FieldTypeEnum.Long)
            .put("note", FieldTypeEnum.String)
            .put("totalStkNum", FieldTypeEnum.Long)
            .put("totalAvlNum", FieldTypeEnum.Long)
            .put("expiredCount", FieldTypeEnum.Long)
            .put("totalPlanEnum", FieldTypeEnum.Long)
            .put("totalPlanFnum", FieldTypeEnum.Long)
            .fields();
    return this.toolMaterialVoService.findAllToolMaterialVos(params, fields);
  }
}
