package com.avic.mti.iron.common.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL 的字符串操作帮助类
 *
 * @author Jinghui Hu
 * @since 2019-12-30, JDK1.8
 */
public class UrlHelper {

  /**
   * Encode 字符串
   *
   * @return 编码后的字符串
   * @author Jinghui Hu
   * @since 2019-12-30, JDK1.8
   */
  public static String encode(String str) {
    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Decode 字符串
   *
   * @return 解码后的字符串
   * @author Jinghui Hu
   * @since 2019-12-30, JDK1.8
   */
  public static String decode(String str) {
    try {
      return URLDecoder.decode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return null;
  }

  private UrlHelper() {}
}
