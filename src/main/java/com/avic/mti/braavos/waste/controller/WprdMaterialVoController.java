package com.avic.mti.iron.waste.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.waste.domain.entity.WprdMaterialVo;
import com.avic.mti.iron.waste.service.WprdMaterialVoService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste/products/view")
public class WprdMaterialVoController {

  public static final Logger logger = LoggerFactory.getLogger(WprdMaterialVoController.class);

  private final WprdMaterialVoService wprdMaterialVoService;

  @Autowired
  public WprdMaterialVoController(WprdMaterialVoService wprdMaterialVoService) {
    this.wprdMaterialVoService = wprdMaterialVoService;
  }

  @GetMapping("")
  public Page<WprdMaterialVo> getWprdMaterialVosPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("wprdCode", FieldTypeEnum.String)
            .put("wprdName", FieldTypeEnum.String)
            .put("wprdCate", FieldTypeEnum.Enumeration)
            .put("wprdSpec", FieldTypeEnum.String)
            .put("wprdType", FieldTypeEnum.Enumeration)
            .put("wprdMaker", FieldTypeEnum.String)
            .put("wprdDept", FieldTypeEnum.String)
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
    return this.wprdMaterialVoService.findWprdMaterialVos(params, fields);
  }

  @GetMapping("/list")
  public List<WprdMaterialVo> getWprdMaterialVosList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("wprdCode", FieldTypeEnum.String)
            .put("wprdName", FieldTypeEnum.String)
            .put("wprdCate", FieldTypeEnum.Enumeration)
            .put("wprdSpec", FieldTypeEnum.String)
            .put("wprdType", FieldTypeEnum.Enumeration)
            .put("wprdMaker", FieldTypeEnum.String)
            .put("wprdDept", FieldTypeEnum.String)
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
    return this.wprdMaterialVoService.findAllWprdMaterialVos(params, fields);
  }
}
