package com.avic.mti.iron.main.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.main.domain.entity.MainMaterialVo;
import com.avic.mti.iron.main.service.MainMaterialVoService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials/view")
public class MainMaterialVoController {

  public static final Logger logger = LoggerFactory.getLogger(MainMaterialVoController.class);

  private final MainMaterialVoService mainMaterialVoService;

  @Autowired
  public MainMaterialVoController(MainMaterialVoService mainMaterialVoService) {
    this.mainMaterialVoService = mainMaterialVoService;
  }

  @GetMapping("")
  public Page<MainMaterialVo> getMainMaterialVosPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("mainCode", FieldTypeEnum.String)
            .put("mainName", FieldTypeEnum.String)
            .put("mainCate", FieldTypeEnum.Enumeration)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mainMaker", FieldTypeEnum.String)
            .put("mainDept", FieldTypeEnum.String)
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
    return this.mainMaterialVoService.findMainMaterialVos(params, fields);
  }

  @GetMapping("/list")
  public List<MainMaterialVo> getMainMaterialVosList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("mainCode", FieldTypeEnum.String)
            .put("mainName", FieldTypeEnum.String)
            .put("mainCate", FieldTypeEnum.Enumeration)
            .put("mainSpec", FieldTypeEnum.String)
            .put("mainType", FieldTypeEnum.Enumeration)
            .put("mainMaker", FieldTypeEnum.String)
            .put("mainDept", FieldTypeEnum.String)
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
    return this.mainMaterialVoService.findAllMainMaterialVos(params, fields);
  }
}
