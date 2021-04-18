package com.avic.mti.iron.common.helper;

import java.util.regex.Pattern;

/**
 * 正则表达式帮助类
 *
 * @author Jinghui Hu
 * @since 2019-06-30, JDK1.8
 */
public class RegExpHelper {

  public static boolean matches(String regex, String str) {
    return str != null && Pattern.matches(regex, str);
  }

  public static boolean isDateTime(String str) {
    return matches(reDateTime1, str)
        || matches(reDateTime2, str)
        || matches(reDateTime3, str)
        || matches(reDateTime4, str);
  }

  public static boolean isDate(String str) {
    return matches(reDate, str);
  }

  // 几种常见日期时间的正则表达式
  private static final String reDateTime1 = "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
  private static final String reDateTime2 =
      "^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}Z$";
  private static final String reDateTime3 =
      "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}$";
  private static final String reDateTime4 =
      "^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}Z$";

  // 日期格式的正则表达式
  private static final String reDate = "^\\d{4}-\\d{1,2}-\\d{1,2}$";

  private RegExpHelper() {}
}
