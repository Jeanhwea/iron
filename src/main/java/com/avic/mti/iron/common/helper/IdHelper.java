package com.avic.mti.iron.common.helper;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 生成随机 ID 的帮助类
 *
 * @author Jinghui Hu
 * @since 2019-05-30, JDK1.8
 */
public class IdHelper {

  /**
   * 获取长度为 length 的字符串，采用的基长度为 base
   *
   * @return 生成的随机字符串
   * @param length 生成的字符串长度
   * @param base 使用的基的长度
   * @author Jinghui Hu
   * @since 2019-06-15, JDK1.8
   */
  private static String getBaseN(int length, int base) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(alphabet[rand.nextInt(base)]);
    }

    return sb.toString();
  }

  /**
   * 按照时间升序随机生成长度为 length 的字符串
   *
   * @return 生成的随机字符串
   * @param length 生成的字符串长度
   * @param time 生成算法所在的时间戳
   * @author Jinghui Hu
   * @since 2019-06-15, JDK1.8
   */
  private static String getOrdinalN(int length, long time) {
    int base = alphabet.length;
    StringBuilder sb = new StringBuilder(length);

    // 前面时间序的部分
    int i = 0;
    for (; i < length; i++) {
      if (time > 0) {
        int idx = (int) (time % (long) base);
        sb.append(alphabet[idx]);
        time = time / base;
      } else {
        break;
      }
    }

    sb.reverse();

    // 后面随机生成增补位部分
    for (; i < length; i++) {
      sb.append(alphabet[rand.nextInt(base)]);
    }

    return sb.toString();
  }

  private static String getChineseN(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int high = (176 + Math.abs(rand.nextInt(39)));
      int low = (161 + Math.abs(rand.nextInt(93)));
      byte[] b = new byte[2];
      b[0] = (new Integer(high).byteValue());
      b[1] = (new Integer(low).byteValue());
      String character;
      try {
        character = new String(b, "GBK");
      } catch (UnsupportedEncodingException e) {
        // e.printStackTrace();
        character = " ";
      }

      sb.append(character);
    }

    return sb.toString();
  }

  public static String getOrdinalN(int length) {
    return getOrdinalN(length, System.currentTimeMillis());
  }

  public static String getBaseN(int length) {
    return getBaseN(length, alphabet.length);
  }

  public static String getDigitalBaseN(int length) {
    return getBaseN(length, 10);
  }

  public static String getZhN(int length) {
    return getChineseN(length);
  }

  private static final char[] alphabet =
      "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

  private static final Random rand = new Random(System.currentTimeMillis());

  private IdHelper() {}
}
