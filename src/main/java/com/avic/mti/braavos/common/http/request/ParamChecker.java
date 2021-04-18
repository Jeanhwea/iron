package com.avic.mti.iron.common.http.request;

import com.avic.mti.iron.common.exception.MissingParameterException;
import com.avic.mti.iron.common.exception.WrongParameterTypeException;
import com.avic.mti.iron.common.helper.RegExpHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 检测传入参数的类
 *
 * @author Jinghui Hu
 * @since 2019-08-26, JDK1.8
 */
public class ParamChecker {

  /**
   * 初始化类
   *
   * @author Jinghui Hu
   * @since 2020-04-20, JDK1.8
   */
  public static ParamChecker init(Map<String, Object> params) {
    return new ParamChecker(params);
  }

  /**
   * 添加一个必须的检查项目
   *
   * @author Jinghui Hu
   * @since 2020-04-20, JDK1.8
   */
  public ParamChecker require(String key, ParamTypeEnum type) {
    required.put(key, type);
    return this;
  }

  /**
   * 检查参数工作，确保参数正确
   *
   * @author Jinghui Hu
   * @since 2020-04-20, JDK1.8
   */
  public void ensure() {
    this.completeSet().wellFormed();
  }

  /**
   * 检查参数的集合是否完整
   *
   * @author Jinghui Hu
   * @since 2020-04-20, JDK1.8
   */
  private ParamChecker completeSet() {
    List<String> missing =
        this.required.keySet().stream()
            .filter(rp -> !this.params.containsKey(rp))
            .collect(Collectors.toList());
    if (!missing.isEmpty()) {
      throw new MissingParameterException(missing);
    }

    return this;
  }

  /**
   * 检查测试的值是否是正确的形式
   *
   * @author Jinghui Hu
   * @since 2020-04-20, JDK1.8
   */
  private void wellFormed() {
    List<String> deformed =
        this.required.keySet().stream()
            .filter(this::testParameterType)
            .collect(Collectors.toList());
    if (!deformed.isEmpty()) {
      throw new WrongParameterTypeException(deformed);
    }
  }

  /**
   * 测试参数类型是否正确
   *
   * @return 返回 true，表示会 key 的格式有问题
   * @author Jinghui Hu
   * @since 2019-09-24, JDK1.8
   */
  private boolean testParameterType(String key) {
    switch (required.get(key)) {
      case Integer:
      case Long:
        return !(this.params.get(key) instanceof Integer);
      case Double:
        return !((this.params.get(key) instanceof Double)
            || (this.params.get(key) instanceof Integer));
      case String:
        return !(this.params.get(key) instanceof String);
      case Datetime:
        return !((this.params.get(key) instanceof String)
            && RegExpHelper.isDateTime((String) this.params.get(key)));
      case Boolean:
        return !(this.params.get(key) instanceof Boolean);
      case List:
        return !(this.params.get(key) instanceof List);
      case Object:
        return !(this.params.get(key) instanceof Map);
      default:
        return false;
    }
  }

  private final Map<String, ParamTypeEnum> required; // 所需要的参数的数组
  private final Map<String, Object> params; // 待检测的参数类别

  public ParamChecker(Map<String, Object> params) {
    this.params = null == params ? new HashMap<>() : params;
    this.required = new HashMap<>();
  }
}
