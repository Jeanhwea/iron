package com.avic.mti.iron.measure.controller;

import com.avic.mti.iron.common.http.request.FieldBuilder;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.measure.domain.entity.MeasMaterialVo;
import com.avic.mti.iron.measure.service.MeasMaterialVoService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measures/view")
public class MeasMaterialVoController {

  public static final Logger logger = LoggerFactory.getLogger(MeasMaterialVoController.class);

  private final MeasMaterialVoService measMaterialVoService;

  @Autowired
  public MeasMaterialVoController(MeasMaterialVoService measMaterialVoService) {
    this.measMaterialVoService = measMaterialVoService;
  }

  @GetMapping("")
  public Page<MeasMaterialVo> getMeasMaterialVosPage(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("measCode", FieldTypeEnum.String)
            .put("measName", FieldTypeEnum.String)
            .put("measCate", FieldTypeEnum.Enumeration)
            .put("measSpec", FieldTypeEnum.String)
            .put("measType", FieldTypeEnum.Enumeration)
            .put("measMaker", FieldTypeEnum.String)
            .put("measDept", FieldTypeEnum.String)
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
    return this.measMaterialVoService.findMeasMaterialVos(params, fields);
  }

  @GetMapping("/list")
  public List<MeasMaterialVo> getMeasMaterialVosList(
      @RequestHeader HttpHeaders headers, @RequestParam Map<String, Object> params) {
    Map<String, FieldTypeEnum> fields =
        FieldBuilder.init()
            .put("measCode", FieldTypeEnum.String)
            .put("measName", FieldTypeEnum.String)
            .put("measCate", FieldTypeEnum.Enumeration)
            .put("measSpec", FieldTypeEnum.String)
            .put("measType", FieldTypeEnum.Enumeration)
            .put("measMaker", FieldTypeEnum.String)
            .put("measDept", FieldTypeEnum.String)
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
    return this.measMaterialVoService.findAllMeasMaterialVos(params, fields);
  }
}
