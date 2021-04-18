package com.avic.mti.iron.common.http.request;

import com.avic.mti.iron.common.exception.WrongParameterFormatException;
import com.avic.mti.iron.common.helper.DateTimeHelper;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;

/**
 * 参数类型转化类
 *
 * @author Jinghui Hu
 * @since 2019-08-26, JDK1.8
 */
public class ParamMapper {

  public static Long toId(Object obj) {
    return toLong(obj);
  }

  public static Long toLong(Object obj) {
    if (obj instanceof Integer) {
      return ((Integer) obj).longValue();
    } else if (obj instanceof Long) {
      return (Long) obj;
    } else if (obj instanceof String) {
      return Long.parseLong((String) obj);
    } else if (obj instanceof BigDecimal) {
      return Long.parseLong(obj.toString());
    } else {
      return null;
    }
  }

  public static Integer toInteger(Object obj) {
    if (obj instanceof Integer) {
      return ((Integer) obj);
    } else if (obj instanceof String) {
      return Integer.parseInt((String) obj);
    } else {
      return null;
    }
  }

  public static Double toDouble(Object obj) {
    if (obj instanceof Double) {
      return (Double) obj;
    } else if (obj instanceof Integer) {
      return Double.valueOf(((Integer) obj));
    } else if (obj instanceof String) {
      return Double.parseDouble((String) obj);
    } else {
      return null;
    }
  }

  public static Timestamp toTimestamp(Object obj) {
    if (obj instanceof String) {
      return timestampOf((String) obj);
    } else {
      return null;
    }
  }

  public static String toString(Object obj) {
    if (obj instanceof String) {
      return (String) obj;
    } else {
      return null;
    }
  }

  public static Boolean toBoolean(Object obj) {
    if (obj instanceof Boolean) {
      return ((Boolean) obj);
    } else if (obj instanceof String) {
      return Boolean.parseBoolean((String) obj);
    }

    return null;
  }

  @SuppressWarnings({"unchecked"})
  public static Map<String, Object> toObject(Object raw) {
    try {
      return (Map<String, Object>) raw;
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings({"unchecked"})
  public static List<Timestamp> toListTime(Object raw) {
    try {
      List<String> timeStringList = (List<String>) raw;
      return timeStringList.stream()
          .map(DateTimeHelper::cvtDatetimeStrToTimestamp)
          .collect(Collectors.toList());
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings({"unchecked"})
  public static List<String> toListString(Object raw) {
    try {
      return (List<String>) raw;
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static List<Long> toListLong(Object raw) {
    try {
      if (raw instanceof List) {
        List alist = (List) raw;
        return (List<Long>) alist.stream().map(ParamMapper::toLong).collect(Collectors.toList());
      } else {
        return null;
      }

    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings({"unchecked"})
  public static List<Map<String, Object>> toListObj(Object raw) {
    try {
      List<Object> objs = (List<Object>) raw;
      return objs.stream().map(ParamMapper::toMapStrObj).collect(Collectors.toList());
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings({"unchecked"})
  public static Map<String, Object> toMapStrObj(Object obj) {
    try {
      return (Map<String, Object>) obj;
    } catch (Exception e) {
      return null;
    }
  }

  private static Timestamp timestampOf(@NonNull String str) {
    if (reJsonTime.matcher(str).matches()) {
      return Timestamp.valueOf(str.substring(0, 10) + " " + str.substring(11, str.length() - 1));
    } else if (reJsonTime2.matcher(str).matches()) {
      return Timestamp.valueOf(str);
    } else {
      throw new WrongParameterFormatException(
          "yyyy-MM-dd hh:mm:ss[.fff] or yyyy-MM-ddThh:mm:ss[.ffff]Z", str);
    }
  }

  // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
  private static final Pattern reJsonTime =
      Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(|\\.[0-9]+)Z");

  // "yyyy-MM-dd HH:mm:ss.SSS"
  private static final Pattern reJsonTime2 =
      Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}(|\\.[0-9]+)");

  private ParamMapper() {}
}
