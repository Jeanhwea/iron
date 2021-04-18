package com.avic.mti.iron.common.helper;

/**
 * 字符串处理帮助类
 *
 * @author Jinghui Hu
 * @since 2020-03-05, JDK1.8
 */
public class StringHelper {

  /**
   * 判断字符串是否为空白
   *
   * @return 如果是空白，返回 true
   * @author Jinghui Hu
   * @since 2020-03-05, JDK1.8
   */
  public static boolean isBlank(String str) {
    return str == null || str.isEmpty();
  }

  /**
   * 判断字符串是否为非空白
   *
   * @return 如果不是空白，返回 true
   * @author Jinghui Hu
   * @since 2020-03-05, JDK1.8
   */
  public static boolean isNonBlank(String str) {
    return str != null && !str.isEmpty();
  }

  public static boolean isNonBlankEqual(String str1, String str2) {
    return str1 != null && str1.equals(str2);
  }
}
