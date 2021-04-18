package com.avic.mti.iron.common.constant;

/**
 * 过滤器次序常量
 *
 * @author Jinghui Hu
 * @since 2020-03-06, JDK1.8
 */
public class FilterOrderConst {
  // 过滤器通用优先级
  public static final int HIGHEST_FILTER_ORDER = 1;
  public static final int MIDDLE_FILTER_ORDER = Integer.MAX_VALUE / 2;
  public static final int LOWEST_FILTER_ORDER = Integer.MAX_VALUE - 1;

  // 常用过滤器级别
  public static final int SYSTEM_FILTER_ORDER = HIGHEST_FILTER_ORDER + 100;
  public static final int APPLICATION_FILTER_ORDER = MIDDLE_FILTER_ORDER + 10;
}
