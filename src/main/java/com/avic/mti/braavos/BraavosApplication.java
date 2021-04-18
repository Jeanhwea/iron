package com.avic.mti.iron;

import com.avic.mti.iron.common.constant.DateTimeConst;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.avic.mti"})
public class BraavosApplication {

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone(DateTimeConst.APPLICATION_TIME_ZONE));
    SpringApplication.run(BraavosApplication.class, args);
  }

  @PostConstruct
  public void setDefaultTimezone() {
    TimeZone.setDefault(TimeZone.getTimeZone(DateTimeConst.APPLICATION_TIME_ZONE));
  }
}
