package com.avic.mti.iron.common.helper;

import java.util.List;

/**
 * Java 容器的帮助类
 *
 * @author Jinghui Hu
 * @since 2021-03-28, JDK1.8
 */
public class ContainerHelper {

  /**
   * 判断列表是否为空
   *
   * @return 如果列表为空，返回 true
   * @author Jinghui Hu
   * @since 2021-03-28, JDK1.8
   */
  public static boolean isEmptyList(List<?> list) {
    return null == list || list.isEmpty();
  }

  /**
   * 判断列表是否为非空
   *
   * @return 如果列表为非空，返回 true
   * @author Jinghui Hu
   * @since 2021-03-28, JDK1.8
   */
  public static boolean isNonEmptyList(List<?> list) {
    return null != list && !list.isEmpty();
  }
}
