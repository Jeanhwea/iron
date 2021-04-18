package com.avic.mti.iron.common.domain.query;

import com.avic.mti.iron.common.domain.entity.MesEntity;
import com.avic.mti.iron.common.http.request.FieldTypeEnum;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * MES 系统中通用条件接口
 *
 * <p>这里需要注意域 (field) 和键 (key) 的区别:
 * <li>1. 域指的是系统实体类变量的名称，例如：age 是域，域通过 FieldBuilder 类来构建
 * <li>2. 键指的是 GET 请求 URL 传入参数的名称，例如： age_eq, age_ne, age_le, age_lt 等等是键
 * <li>3. 通常一个域可能对应多个键
 * <li>4. 键放在 params 中，是该 Map 的索引
 *
 * @author Jinghui Hu
 * @since 2020-04-14, JDK1.8
 */
public interface MesConditionBuilder<E extends MesEntity> {

  /**
   * 初始化参数
   *
   * @author Jinghui Hu
   * @since 2020-04-14, JDK1.8
   */
  MesConditionBuilder<E> init(Map<String, Object> params, Map<String, FieldTypeEnum> fields);

  /**
   * 构建的过滤排序的条件参数
   *
   * @author Jinghui Hu
   * @since 2020-04-14, JDK1.8
   */
  Specification<E> spec();

  /**
   * 构建的过滤排序的条件参数，添加主键过滤子集
   *
   * @author Jinghui Hu
   * @since 2020-06-10, JDK1.8
   */
  Specification<E> spec(List<Long> ids);

  /**
   * 构建的过滤排序的条件参数，添加排除主键过滤子集
   *
   * @author Jinghui Hu
   * @since 2020-07-16, JDK1.8
   */
  Specification<E> specExclude(List<Long> ids);

  /**
   * 构建分页的条件参数, 构建分页时返回两个参数
   * <li>1. 页码 Page Number, 从第 0 页开始计算
   * <li>2. 页大小 Page Size, 每页有最大数据量
   *
   * @author Jinghui Hu
   * @since 2020-04-18, JDK1.8
   */
  Pageable page();
}
