package com.avic.mti.iron.common.domain.query.impl;

import com.avic.mti.iron.common.domain.entity.LogEntity;
import com.avic.mti.iron.common.domain.query.LogConditionBuilder;
import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.helper.StringHelper;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import com.avic.mti.iron.common.http.request.ParamMapper;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 过滤，排序的组合条件查询的条件构建类
 *
 * <p>该实现类中需要这里注意域 (field) 和键 (key) 的区别:
 * <li>1. 域指的是系统实体类变量的名称，例如: age 是域
 * <li>2. 键指的是 GET 请求 URL 传入参数的名称，例如: age_eq, age_ne, age_le, age_lt 等等是键
 * <li>3. 通常一个域可能对应多个键
 *
 * @author Jinghui Hu
 * @since 2021-01-18, JDK1.8
 */
@Component
@Scope("prototype")
public class LogConditionBuilderImpl<E extends LogEntity> implements LogConditionBuilder<E> {

  public static final Logger logger = LoggerFactory.getLogger(LogConditionBuilderImpl.class);

  // JPA 条件构建的基本组件
  Root<E> condRoot;
  CriteriaQuery<?> condQuery;
  CriteriaBuilder condBuilder;
  List<Predicate> predicates;
  List<Order> orders;
  List<Sort.Order> sortOrders;

  // MES 中条件参数和条件检查列表
  Map<String, Object> queryParams;
  Map<String, FieldTypeEnum> queryFields;

  // 所有识别的操作符后缀
  private static final List<String> FILTER_KEY_SUFFIXES =
      Arrays.asList("lk", "sw", "ew", "in", "ni", "eq", "ne", "gt", "ge", "lt", "le");

  // 一些通用的键名称
  private static final String PRIMARY_KEY = "id";
  private static final String KEY_ORDER_BY = "ob";
  private static final String KEY_PAGE_SIZE = "ps";
  private static final String KEY_PAGE_NUMBER = "pn";

  // 页面固定值
  private static final String PAGE_FIX_FIELD = "id";

  /**
   * 提取键所包含的域
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private static String extractFieldFromKey(@NonNull String key) {
    int lastIdx = key.lastIndexOf('_');
    String suffix = lastIdx > 0 ? key.substring(lastIdx + 1) : "";
    return FILTER_KEY_SUFFIXES.contains(suffix) ? key.substring(0, lastIdx) : key;
  }

  /**
   * 提取键的操作后缀
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private static String extractSuffixFromKey(@NonNull String key) {
    int lastIdx = key.lastIndexOf('_');
    return lastIdx > 0 ? key.substring(lastIdx + 1) : "";
  }

  /**
   * 返回真，如果 key 是已知的键
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private boolean isKnownKey(@NonNull String key) {
    return this.queryFields.get(extractFieldFromKey(key)) != null;
  }

  /**
   * 获取 String 类型的值
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private String readStringArg(@NonNull String key) {
    return Optional.ofNullable(queryParams.get(key))
        .map(ParamMapper::toString)
        .map(str -> str.replaceAll("%", "%%"))
        .orElseThrow(() -> new BadRequestException("字符串类型参数 {0} 的值有误", key));
  }

  /**
   * 获取 Long 类型的单操作数
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private Long readUnaryLongArg(@NonNull String key) {
    return Optional.ofNullable(queryParams.get(key))
        .map(ParamMapper::toLong)
        .orElseThrow(() -> new BadRequestException("长整数类型参数 {0} 的值有误", key));
  }

  /**
   * 获取 Long 类型的双操作数
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private List<Long> readBinaryLongArgs(@NonNull String key) {
    List<Long> args =
        Optional.ofNullable(queryParams.get(key))
            .map(ParamMapper::toString)
            .map(s -> Arrays.asList(s.split("[ +]")))
            .map(arg -> arg.stream().map(ParamMapper::toLong).collect(Collectors.toList()))
            .orElseThrow(() -> new BadRequestException("长整数类型参数 {0} 的值有误", key));
    if (args == null || args.size() != 2) {
      throw new BadRequestException("长整数类型参数 {0} 的值有误，值应该有且仅有两个参数，需要通过 + 号连接");
    }

    return args;
  }

  /**
   * 获取 Double 类型的单操作数
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private Double readUnaryDoubleArg(@NonNull String key) {
    return Optional.ofNullable(queryParams.get(key))
        .map(ParamMapper::toDouble)
        .orElseThrow(() -> new BadRequestException("双精度类型参数 {0} 的值有误", key));
  }

  /**
   * 获取 Double 类型的双操作数
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private List<Double> readBinaryDoubleArgs(@NonNull String key) {
    List<Double> args =
        Optional.ofNullable(queryParams.get(key))
            .map(ParamMapper::toString)
            .map(s -> Arrays.asList(s.split("[ +]")))
            .map(a -> a.stream().map(ParamMapper::toDouble).collect(Collectors.toList()))
            .orElseThrow(() -> new BadRequestException("双精度类型参数 {0} 的值有误", key));
    if (args == null || args.size() != 2) {
      throw new BadRequestException("双精度类型参数 {0} 的值有误，值应该有且仅有两个参数，需要通过 + 号连接");
    }

    return args;
  }

  /**
   * 获取 Timestamp 类型的单操作数
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private Timestamp readTimestampArg(@NonNull String key) {
    return Optional.ofNullable(queryParams.get(key))
        .map(ParamMapper::toTimestamp)
        .orElseThrow(() -> new BadRequestException("日期类型参数 {0} 的值有误", key));
  }

  /**
   * 获取枚举类型的单操作数
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private String readUnaryEnumArg(@NonNull String key) {
    return Optional.ofNullable(queryParams.get(key))
        .map(ParamMapper::toString)
        .orElseThrow(() -> new BadRequestException("枚举类型参数 {0} 的值有误", key));
  }

  /**
   * 获取枚举类型的操作数列表
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private List<String> readEnumListArgs(@NonNull String key) {
    List<String> args =
        Optional.ofNullable(queryParams.get(key))
            .map(ParamMapper::toString)
            .map(str -> Arrays.asList(str.split("[ +]")))
            .map(
                a ->
                    a.stream()
                        .map(ParamMapper::toString)
                        .filter(StringHelper::isNonBlank)
                        .collect(Collectors.toList()))
            .orElseThrow(() -> new BadRequestException("枚举类型参数 {0} 的值有误", key));
    if (args == null || args.isEmpty()) {
      throw new BadRequestException("枚举类型参数 {0} 的值有误，值应该至少传入一个数值");
    }

    return args;
  }

  /**
   * 获取布尔类型的操作数
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private Boolean readBooleanArg(@NonNull String key) {
    return Optional.ofNullable(queryParams.get(key))
        .map(ParamMapper::toBoolean)
        .orElseThrow(() -> new BadRequestException("布尔类型参数 {0} 的值有误", key));
  }

  /**
   * 添加字符串类型的过滤器， LIKE 类型的表达式
   *
   * @author Jinghui Hu
   * @since 2020-04-14, JDK1.8
   */
  private void handleStringFilter(@NonNull String key) {
    String field = extractFieldFromKey(key);
    String suffix = extractSuffixFromKey(key);
    String val = readStringArg(key);

    logger.trace("过滤的 {} 字段的名称为 {}, 字符串模糊查询条件为 {}", key, field, val);
    Expression<String> strExpr = condRoot.get(field).as(String.class);
    if ("eq".equals(suffix) || StringHelper.isBlank(suffix)) {
      if (StringHelper.isBlank(val)) {
        predicates.add(condBuilder.and(condBuilder.isNull(strExpr)));
      } else {
        predicates.add(condBuilder.and(condBuilder.equal(strExpr, val)));
      }

    } else if ("lk".equals(suffix)) {
      predicates.add(condBuilder.and(condBuilder.like(strExpr, "%" + val + "%")));
    } else if ("sw".equals(suffix)) {
      predicates.add(condBuilder.and(condBuilder.like(strExpr, val + "%")));
    } else if ("ew".equals(suffix)) {
      predicates.add(condBuilder.and(condBuilder.like(strExpr, "%" + val)));
    } else {
      logger.error("未知的字符串类型的过滤字段: {}", key);
    }
  }

  /**
   * 添加 Long 类型的过滤器，支持文档中对应的后缀操作符
   *
   * @author Jinghui Hu
   * @since 2020-04-14, JDK1.8
   */
  private void handleLongFilter(@NonNull String key) {
    String field = extractFieldFromKey(key);
    String suffix = extractSuffixFromKey(key);

    logger.trace("过滤长整数类型的 {} 字段的名称为 {}, {}", key, field, suffix);
    Expression<Long> longExpr = condRoot.get(field).as(Long.class);
    if ("eq".equals(suffix) || StringHelper.isBlank(suffix)) {
      Long val = readUnaryLongArg(key);
      predicates.add(condBuilder.and(condBuilder.equal(longExpr, val)));
    } else if ("ne".equals(suffix)) {
      Long val = readUnaryLongArg(key);
      predicates.add(condBuilder.and(condBuilder.notEqual(longExpr, val)));
    } else if ("in".equals(suffix)) {
      List<Long> args = readBinaryLongArgs(key);
      Long minVal = args.get(0);
      Long maxVal = args.get(1);
      predicates.add(condBuilder.and(condBuilder.between(longExpr, minVal, maxVal)));
    } else if ("ni".equals(suffix)) {
      List<Long> args = readBinaryLongArgs(key);
      Long minVal = args.get(0);
      Long maxVal = args.get(1);
      predicates.add(
          condBuilder.and(condBuilder.not(condBuilder.between(longExpr, minVal, maxVal))));
    } else if ("gt".equals(suffix)) {
      Long val = readUnaryLongArg(key);
      predicates.add(condBuilder.and(condBuilder.greaterThan(longExpr, val)));
    } else if ("ge".equals(suffix)) {
      Long val = readUnaryLongArg(key);
      predicates.add(condBuilder.and(condBuilder.greaterThanOrEqualTo(longExpr, val)));
    } else if ("lt".equals(suffix)) {
      Long val = readUnaryLongArg(key);
      predicates.add(condBuilder.and(condBuilder.lessThan(longExpr, val)));
    } else if ("le".equals(suffix)) {
      Long val = readUnaryLongArg(key);
      predicates.add(condBuilder.and(condBuilder.lessThanOrEqualTo(longExpr, val)));
    } else {
      logger.error("未知的长整数类型的过滤字段: {}", key);
    }
  }

  /**
   * 添加 Double 类型的过滤器，支持文档中对应的后缀操作符
   *
   * @author Jinghui Hu
   * @since 2020-04-14, JDK1.8
   */
  private void handleDoubleFilter(@NonNull String key) {
    String field = extractFieldFromKey(key);
    String suffix = extractSuffixFromKey(key);

    logger.trace("过滤双精度类型的 {} 字段的名称为 {}, {}", key, field, suffix);
    Expression<Double> doubleExpr = condRoot.get(field).as(Double.class);
    if ("eq".equals(suffix) || StringHelper.isBlank(suffix)) {
      Double val = readUnaryDoubleArg(key);
      predicates.add(condBuilder.and(condBuilder.equal(doubleExpr, val)));
    } else if ("ne".equals(suffix)) {
      Double val = readUnaryDoubleArg(key);
      predicates.add(condBuilder.and(condBuilder.notEqual(doubleExpr, val)));
    } else if ("in".equals(suffix)) {
      List<Double> args = readBinaryDoubleArgs(key);
      Double minVal = args.get(0);
      Double maxVal = args.get(1);
      predicates.add(condBuilder.and(condBuilder.between(doubleExpr, minVal, maxVal)));
    } else if ("ni".equals(suffix)) {
      List<Double> args = readBinaryDoubleArgs(key);
      Double minVal = args.get(0);
      Double maxVal = args.get(1);
      predicates.add(
          condBuilder.and(condBuilder.not(condBuilder.between(doubleExpr, minVal, maxVal))));
    } else if ("gt".equals(suffix)) {
      Double val = readUnaryDoubleArg(key);
      predicates.add(condBuilder.and(condBuilder.greaterThan(doubleExpr, val)));
    } else if ("ge".equals(suffix)) {
      Double val = readUnaryDoubleArg(key);
      predicates.add(condBuilder.and(condBuilder.greaterThanOrEqualTo(doubleExpr, val)));
    } else if ("lt".equals(suffix)) {
      Double val = readUnaryDoubleArg(key);
      predicates.add(condBuilder.and(condBuilder.lessThan(doubleExpr, val)));
    } else if ("le".equals(suffix)) {
      Double val = readUnaryDoubleArg(key);
      predicates.add(condBuilder.and(condBuilder.lessThanOrEqualTo(doubleExpr, val)));
    } else {
      logger.error("未知的双精度类型的过滤字段: {}", key);
    }
  }

  /**
   * 添加日期类型的过滤器，支持文档中对应的后缀操作符
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private void handleTimestampFilter(@NonNull String key) {
    String field = extractFieldFromKey(key);
    String suffix = extractSuffixFromKey(key);
    Timestamp val = readTimestampArg(key);

    logger.trace("过滤日期类型的 {} 字段的名称为 {}, {}: {}", key, field, suffix, val);
    Expression<Timestamp> stmpExpr = condRoot.get(field).as(Timestamp.class);
    if ("eq".equals(suffix) || StringHelper.isBlank(suffix)) {
      predicates.add(condBuilder.and(condBuilder.equal(stmpExpr, val)));
    } else if ("gt".equals(suffix)) {
      predicates.add(condBuilder.and(condBuilder.greaterThan(stmpExpr, val)));
    } else if ("ge".equals(suffix)) {
      predicates.add(condBuilder.and(condBuilder.greaterThanOrEqualTo(stmpExpr, val)));
    } else if ("lt".equals(suffix)) {
      predicates.add(condBuilder.and(condBuilder.lessThan(stmpExpr, val)));
    } else if ("le".equals(suffix)) {
      predicates.add(condBuilder.and(condBuilder.lessThanOrEqualTo(stmpExpr, val)));
    } else {
      logger.error("未知的日期类型的过滤字段: {}", key);
    }
  }

  /**
   * 添加枚举类型的过滤器
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private void handleEnumFilter(@NonNull String key) {
    String field = extractFieldFromKey(key);
    String suffix = extractSuffixFromKey(key);

    logger.trace("过滤枚举类型的 {} 字段的名称为 {}, {}", key, field, suffix);
    if ("eq".equals(suffix) || StringHelper.isBlank(suffix)) {
      String val = readUnaryEnumArg(key);
      Expression<String> eunmExpr = condRoot.get(field).as(String.class);
      predicates.add(condBuilder.and(condBuilder.equal(eunmExpr, val)));
    } else if ("ne".equals(suffix)) {
      String val = readUnaryEnumArg(key);
      Expression<String> eunmExpr = condRoot.get(field).as(String.class);
      predicates.add(condBuilder.and(condBuilder.notEqual(eunmExpr, val)));
    } else if ("in".equals(suffix)) {
      List<String> vals = readEnumListArgs(key);
      predicates.add(condBuilder.and(condRoot.get(field).in(vals)));
    } else if ("ni".equals(suffix)) {
      List<String> vals = readEnumListArgs(key);
      predicates.add(condBuilder.and(condBuilder.not(condRoot.get(field).in(vals))));
    } else {
      logger.error("未知的枚举类型的过滤字段: {}", key);
    }
  }

  /**
   * 添加布尔类型的过滤器
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private void handleBooleanFilter(@NonNull String key) {
    String field = extractFieldFromKey(key);
    Expression<String> boolExpr = condRoot.get(field).as(String.class);
    Boolean val = readBooleanArg(key);
    logger.trace("过滤枚举类型的 {} 字段的名称为 {}: {}", key, field, val);
    if (val) {
      predicates.add(condBuilder.and(condBuilder.equal(boolExpr, "y")));
    } else {
      predicates.add(condBuilder.and(condBuilder.notEqual(boolExpr, "y")));
    }
  }

  /**
   * 添加主键的过滤器
   *
   * @author Jinghui Hu
   * @since 2020-06-10, JDK1.8
   */
  private void handlePrimaryKeyFilter(@NonNull List<Long> ids) {
    logger.trace("过滤主键类型的字段, 共记 {} 条主键列表", ids.size());
    if (!ids.isEmpty()) {
      predicates.add(condBuilder.and(condRoot.get(PRIMARY_KEY).in(ids)));
    }
  }

  /**
   * 添加一个过滤的键到查询条件池中
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private void addFilterKey(@NonNull String key) {
    String field = extractFieldFromKey(key);
    FieldTypeEnum type =
        Optional.ofNullable(queryFields.get(field))
            .orElseThrow(() -> new BadRequestException("未知的过滤字段: {0}", key));

    switch (type) {
      case String:
        handleStringFilter(key);
        break;
      case Long:
        handleLongFilter(key);
        break;
      case Double:
        handleDoubleFilter(key);
        break;
      case Timestamp:
        handleTimestampFilter(key);
        break;
      case Enumeration:
        handleEnumFilter(key);
        break;
      case Boolean:
        handleBooleanFilter(key);
        break;
      case Unknown:
      default:
        logger.error("未知的 Query 域: {}", key);
        throw new BadRequestException("未知的 Query 域: {0}", key);
    }
  }

  /**
   * 添加一个排序键到排序池中
   *
   * @author Jinghui Hu
   * @since 2020-04-15, JDK1.8
   */
  private void addOrderKey(String key) {
    String field = key.replaceAll("^[ \\-+]", "");
    FieldTypeEnum type =
        Optional.ofNullable(queryFields.get(field))
            .orElseThrow(() -> new BadRequestException("未知的排序字段: {0}", key));
    boolean isDesc = key.startsWith("-");
    logger.trace("排序的 {} 字段名称为 {}, 类型为 {}, 是否降序条件为 {}", key, field, type, isDesc);

    Expression<?> expr;
    switch (type) {
      case String:
      case Enumeration:
        expr = condRoot.get(field).as(String.class);
        break;
      case Timestamp:
        expr = condRoot.get(field).as(Timestamp.class);
        break;
      case Long:
        expr = condRoot.get(field).as(Long.class);
        break;
      case Double:
        expr = condRoot.get(field).as(Double.class);
        break;
      case Boolean:
        expr = condRoot.get(field).as(Boolean.class);
        break;
      case Unknown:
      default:
        logger.error("未知的 Query 类型: {}", type);
        throw new BadRequestException("未知的 Query 类型: {0}", type);
    }

    if (isDesc) {
      this.orders.add(condBuilder.desc(expr));
    } else {
      this.orders.add(condBuilder.asc(expr));
    }
  }

  private void addSortKey(String key) {
    String field = key.replaceAll("^[ \\-+]", "");
    boolean isDesc = key.startsWith("-");
    if (isDesc) {
      this.sortOrders.add(new Sort.Order(Sort.Direction.DESC, field));
    } else {
      this.sortOrders.add(new Sort.Order(Sort.Direction.ASC, field));
    }
  }

  /**
   * 处理所有的过滤字段，分发过滤字段处理任务
   *
   * @author Jinghui Hu
   * @since 2020-04-14, JDK1.8
   */
  private void doFilter() {
    Optional.ofNullable(this.queryParams)
        .map(p -> p.keySet().stream().filter(this::isKnownKey).collect(Collectors.toList()))
        .ifPresent(
            filterList -> {
              logger.trace("获取所有过滤的字段列表: {}", filterList);
              filterList.forEach(this::addFilterKey);
              this.condQuery.where(condBuilder.and(predicates.toArray(new Predicate[0])));
            });
  }

  /**
   * 处理所有排序的字段
   *
   * @author Jinghui Hu
   * @since 2020-04-14, JDK1.8
   */
  private void doOrder() {
    Optional.ofNullable(this.queryParams)
        .map(p -> p.get(KEY_ORDER_BY))
        .map(ParamMapper::toString)
        .map(s -> Arrays.asList(s.split(",")))
        .ifPresent(
            orderList -> {
              logger.trace("获取的所有排序字段列表: {}", orderList);
              orderList.forEach(this::addOrderKey);
            });

    this.orders.add(condBuilder.asc(condRoot.get(PAGE_FIX_FIELD).as(Long.class)));
    this.condQuery.orderBy(this.orders);
  }

  private void doSort() {
    Optional.ofNullable(this.queryParams)
        .map(p -> p.get(KEY_ORDER_BY))
        .map(ParamMapper::toString)
        .map(s -> Arrays.asList(s.split(",")))
        .ifPresent(
            orderList -> {
              logger.trace("获取的所有排序字段列表: {}", orderList);
              orderList.forEach(this::addSortKey);
            });
  }

  @Override
  public LogConditionBuilder<E> init(
      Map<String, Object> params, Map<String, FieldTypeEnum> fields) {
    LogConditionBuilderImpl<E> builder = new LogConditionBuilderImpl<>();
    builder.queryParams = params;
    builder.queryFields = fields;
    return builder;
  }

  @Override
  public Specification<E> spec() {
    return (root, query, builder) -> {
      logger.trace("初始化条件的基本组件");
      this.condRoot = root;
      this.condQuery = query;
      this.condBuilder = builder;
      this.predicates = new LinkedList<>();
      this.orders = new LinkedList<>();

      logger.trace("调用构造过滤和排序的处理函数");
      this.doFilter();
      this.doOrder();

      logger.trace("返回构造查询条件的结构");
      return query.getRestriction();
    };
  }

  @Override
  public Specification<E> spec(List<Long> ids) {
    return (root, query, builder) -> {
      logger.trace("初始化条件的基本组件");
      this.condRoot = root;
      this.condQuery = query;
      this.condBuilder = builder;
      this.predicates = new LinkedList<>();
      this.orders = new LinkedList<>();
      this.handlePrimaryKeyFilter(ids);

      logger.trace("调用构造过滤和排序的处理函数");
      this.doFilter();
      this.doOrder();

      logger.trace("返回构造查询条件的结构");
      return query.getRestriction();
    };
  }

  @Override
  public Pageable page() {
    this.sortOrders = new LinkedList<>();
    this.doSort();
    int number =
        Optional.ofNullable(this.queryParams)
            .map(p -> p.get(KEY_PAGE_NUMBER))
            .map(ParamMapper::toInteger)
            .orElse(0);
    int size =
        Optional.ofNullable(this.queryParams)
            .map(p -> p.get(KEY_PAGE_SIZE))
            .map(ParamMapper::toInteger)
            .orElse(10);
    this.sortOrders.add(new Sort.Order(Sort.Direction.ASC, PAGE_FIX_FIELD));
    return PageRequest.of(number, size, Sort.by(this.sortOrders));
  }
}
