package com.avic.mti.iron;

import com.avic.mti.iron.common.constant.DateTimeConst;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.avic.mti"})
public class IronApplication {

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone(DateTimeConst.APPLICATION_TIME_ZONE));
    SpringApplication.run(IronApplication.class, args);
  }

  @PostConstruct
  public void setDefaultTimezone() {
    TimeZone.setDefault(TimeZone.getTimeZone(DateTimeConst.APPLICATION_TIME_ZONE));
  }
}
