package com.avic.mti.iron.common.helper;

import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.lang.NonNull;

public class NameCodeHelper {

  private static final Pattern reNameCode = Pattern.compile("^[^\\[]+\\[[-0-9A-Za-z_]+]$");

  /**
   * 判断是否符合名称和代码规范
   *
   * @author Jinghui Hu
   * @since 2020-04-21, JDK1.8
   */
  public static boolean isNameCode(String str) {
    return str != null && reNameCode.matcher(str).matches();
  }

  /**
   * 通过名称和编码构造 NameCode
   *
   * @author Jinghui Hu
   * @since 2020-04-26, JDK1.8
   */
  public static String consNameCode(@NonNull String name, @NonNull String code) {
    return name + "[" + code + "]";
  }

  /**
   * 提取名称子字符串
   *
   * @author Jinghui Hu
   * @since 2020-04-26, JDK1.8
   */
  public static Optional<String> extractName(@NonNull String nameCode) {
    if (!isNameCode(nameCode)) {
      return Optional.empty();
    }

    int a = nameCode.indexOf('[');
    return Optional.of(nameCode.substring(0, a));
  }

  /**
   * 提取编码子字符串
   *
   * @author Jinghui Hu
   * @since 2020-04-26, JDK1.8
   */
  public static Optional<String> extractCode(@NonNull String nameCode) {
    if (!isNameCode(nameCode)) {
      return Optional.empty();
    }

    int a = nameCode.indexOf('[');
    int b = nameCode.lastIndexOf(']');
    return Optional.of(nameCode.substring(a + 1, b));
  }

  /**
   * 翻转名称和编码的顺序
   *
   * @author Jinghui Hu
   * @since 2020-04-26, JDK1.8
   */
  public static Optional<String> flipNameCode(@NonNull String nameCode) {
    if (!isNameCode(nameCode)) {
      return Optional.empty();
    }

    int a = nameCode.indexOf('[');
    int b = nameCode.lastIndexOf(']');
    return Optional.of(consNameCode(nameCode.substring(a + 1, b), nameCode.substring(0, a)));
  }

  /**
   * 校验名称编码字段是否正确
   *
   * @author Jinghui Hu
   * @since 2020-05-20, JDK1.8
   */
  public static boolean validateNameCode(String str) {
    return StringHelper.isNonBlank(str) && !isNameCode(str);
  }

  /**
   * 断言名称编码正确
   *
   * @author Jinghui Hu
   * @since 2020-05-20, JDK1.8
   */
  public static boolean assertNameCode(String str) {
    return isNameCode(str);
  }
}
