package com.avic.mti.iron.main.service;

import static org.junit.jupiter.api.Assertions.*;

import com.avic.mti.iron.common.helper.DateTimeHelper;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import com.avic.mti.iron.main.domain.entity.MainIn;
import com.avic.mti.iron.main.domain.entity.MainMaterial;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainInServiceTest {

  @Autowired MainMaterialService mainMaterialService;
  @Autowired MainInService mainInService;

  @Disabled
  @Test
  void createMainIn() {
    List<MainMaterial> idleMainMaterials = mainMaterialService.findIdleMainMaterials();
    for (int i = 0; i < 100; i++) {
      MainMaterial mainMaterial = idleMainMaterials.get(i % idleMainMaterials.size());
      int tag = i + 1;
      int jCount = (int) Math.ceil(Math.random() * 10);
      for (int j = 0; j < jCount; j++) {
        String mtlBatch = String.format("MIB%06d", i * 1000 + j);
        int stkNum = (int) Math.ceil(Math.random() * 100);
        double mainPrice = Math.random() * 100 + 1;
        Map<String, Object> params =
            ParamBuilder.init()
                .put("updateUser", "测试用例[tester]")
                .put("inCode", String.format("IN%04d/%s", tag, mtlBatch))
                .put("mtlBatch", mtlBatch)
                .put("mainId", mainMaterial.id())
                .put("mainName", mainMaterial.mainName())
                .put("mainType", mainMaterial.mainType())
                .put("inType", "1-手工")
                .put("inCate", "1-入库")
                .put("retestStatus", "1-合格")
                .put("inNC", "胡京徽[zg202599]")
                .put("inDate", DateTimeHelper.cvtDatetimeToString(DateTimeHelper.now()))
                .put("note", "测试数据")
                .put("shelfJson", "[]")
                .put("mainPrice", mainPrice)
                .put("mainStkNum", stkNum)
                .put("mainAvlNum", stkNum)
                .put("mainPlnNum", 0)
                .params();
        MainIn mainIn = mainInService.createMainIn(params);
        assertNotNull(mainIn);
      }
    }
  }
}
