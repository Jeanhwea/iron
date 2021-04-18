package com.avic.mti.iron.common.helper;

import com.avic.mti.iron.common.constant.DateTimeConst;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 处理日期和时间相关格式转换的帮助类 DateTime Helper
 *
 * @author Jinghui Hu
 * @since 2019-05-30, JDK1.8
 */
public class DateTimeHelper {

  public static String cvtDateToString(Timestamp value) {
    try {
      return new SimpleDateFormat(DATE_STRING_FORMAT).format(new Date(value.getTime()));
    } catch (Exception e) {
      return null;
    }
  }

  public static String cvtDatetimeToString(Timestamp value) {
    try {
      return new SimpleDateFormat(DATETIME_STRING_FORMAT).format(new Date(value.getTime()));
    } catch (Exception e) {
      return null;
    }
  }

  public static Timestamp cvtDateStrToTimestamp(String str) {
    if (!RegExpHelper.isDate(str)) {
      throw new RuntimeException("日期格式不满足 yyyy-MM-dd 格式： " + str);
    }

    try {
      return Timestamp.valueOf(str + " 00:00:00");
    } catch (Exception e) {
      return null;
    }
  }

  public static Timestamp cvtDatetimeStrToTimestamp(String str) {
    if (!RegExpHelper.isDateTime(str)) {
      throw new RuntimeException("日期格式不满足 yyyy-MM-dd HH:mm:ss 格式： " + str);
    }

    try {
      return Timestamp.valueOf(str.replace('T', ' ').replace('Z', ' '));
    } catch (Exception e) {
      return null;
    }
  }

  public static String getDayOfWeek(Timestamp value) {
    Calendar theCalendar = Calendar.getInstance();
    try {
      theCalendar.setTime(value);
      return WEEKDAYS_LIST[(theCalendar.get(Calendar.DAY_OF_WEEK) - 1) % 7];
    } catch (Exception e) {
      return null;
    }
  }

  public static Timestamp now() {
    return new Timestamp(System.currentTimeMillis());
  }

  public static Timestamp today() {
    return truncDate(now());
  }

  public static int currYear() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.YEAR);
  }

  public static int currMonth() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.MONTH) + 1;
  }

  public static int currDay() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DATE);
  }

  public static Timestamp truncDate(Timestamp value) {
    return new Timestamp(
        value.getTime() / DateTimeConst.MILLISECONDS_PER_DAY * DateTimeConst.MILLISECONDS_PER_DAY);
  }

  public static Timestamp nextDate(Timestamp value) {
    return new Timestamp(value.getTime() + DateTimeConst.MILLISECONDS_PER_DAY);
  }

  public static Timestamp prevDate(Timestamp value) {
    return new Timestamp(value.getTime() - DateTimeConst.MILLISECONDS_PER_DAY);
  }

  public static Timestamp shiftDateN(Timestamp value, int dayCount) {
    return new Timestamp(value.getTime() + (long) dayCount * DateTimeConst.MILLISECONDS_PER_DAY);
  }

  public static String ymAsString() {
    try {
      return new SimpleDateFormat(YM_STRING_FORMAT).format(new Date());
    } catch (Exception e) {
      return null;
    }
  }

  public static String ymdAsString() {
    try {
      return new SimpleDateFormat(YMD_STRING_FORMAT).format(new Date());
    } catch (Exception e) {
      return null;
    }
  }

  public static String dateAsString() {
    try {
      return new SimpleDateFormat(DATE_STRING_FORMAT).format(new Date());
    } catch (Exception e) {
      return null;
    }
  }

  public static String nowAsString() {
    try {
      return new SimpleDateFormat(DATETIME_STRING_FORMAT).format(new Date());
    } catch (Exception e) {
      return null;
    }
  }

  private static final String YMD_STRING_FORMAT = "yyyyMMdd";
  private static final String YM_STRING_FORMAT = "yyyyMM";
  private static final String DATE_STRING_FORMAT = "yyyy-MM-dd";
  private static final String DATETIME_STRING_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final String[] WEEKDAYS_LIST = {
    "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
  };

  private DateTimeHelper() {}
}
