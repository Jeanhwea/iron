package com.avic.mti.iron.main.service;

import static org.junit.jupiter.api.Assertions.*;

import com.avic.mti.iron.common.helper.DateTimeHelper;
import com.avic.mti.iron.common.http.request.ParamBuilder;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainMaterialServiceTest {

  @Autowired MainMaterialService mainMaterialService;

  @Disabled
  @Test
  void createMainMaterial() {
    String[] nameList = new String[] {"芳纶纸", "节点胶", "酚醛树脂", "其它主材"};
    String[] typeList = new String[] {"1-芳纶纸", "2-节点胶", "3-酚醛树脂", "9-其它"};
    for (int i = 1000; i < 2000; i++) {
      int choose = (int) Math.ceil(Math.random() * 4) % 4;
      int tag = i + 1;
      Map<String, Object> params =
          ParamBuilder.init()
              .put("updateUser", "测试用例[tester]")
              .put("mainCode", String.format("ZC-%04d", tag))
              .put("mainName", String.format("%s %04d", nameList[choose], tag))
              // .put("mainCate", "1-进口")
              .put("mainCate", "2-国产")
              .put("mainType", typeList[choose])
              .put("inType", "1-手工")
              .put("inNC", "胡京徽[zg202599]")
              .put("inDate", DateTimeHelper.cvtDatetimeToString(DateTimeHelper.now()))
              .put("note", "测试数据")
              .params();
      mainMaterialService.createMainMaterial(params);
    }
  }
}
