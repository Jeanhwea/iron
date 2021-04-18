package com.avic.mti.iron.common.helper;

import com.avic.mti.iron.common.http.request.ParamMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;

/**
 * Json 和字符串转化的帮助类
 *
 * @author Jinghui Hu
 * @since 2019-12-30, JDK1.8
 */
public class JsonHelper {

  private JsonHelper() {}

  @SuppressWarnings("unchecked")
  public static Optional<Map<String, Object>> parseObject(@NonNull String str) {
    ObjectMapper mapper = getObjectMapper();
    Map<String, Object> obj = null;
    try {
      if (StringHelper.isNonBlank(str)) {
        obj = mapper.readValue(str, Map.class);
      }

    } catch (IOException e) {
      // e.printStackTrace();
    }

    return Optional.ofNullable(obj);
  }

  public static Optional<String> stringifyObject(@NonNull Map<String, Object> obj) {
    ObjectMapper mapper = getObjectMapper();

    String str = null;
    try {
      str = mapper.writeValueAsString(obj);
    } catch (Exception e) {
      // e.printStackTrace();
    }

    return Optional.ofNullable(str);
  }

  @SuppressWarnings("unchecked")
  public static Optional<List<Map<String, Object>>> parseList(@NonNull String str) {
    ObjectMapper mapper = getObjectMapper();

    List<Map<String, Object>> obj = null;
    try {
      if (StringHelper.isNonBlank(str)) {
        obj = mapper.readValue(str, List.class);
      }

    } catch (IOException e) {
      // e.printStackTrace();
    }

    return Optional.ofNullable(obj);
  }

  public static Optional<String> stringifyList(@NonNull List<Map<String, Object>> obj) {
    ObjectMapper mapper = getObjectMapper();

    String str = null;
    try {
      str = mapper.writeValueAsString(obj);
    } catch (Exception e) {
      // e.printStackTrace();
    }

    return Optional.ofNullable(str);
  }

  @SuppressWarnings("unchecked")
  public static Optional<List<String>> parseListStrings(@NonNull String str) {
    ObjectMapper mapper = getObjectMapper();

    List<String> obj = null;
    try {
      if (StringHelper.isNonBlank(str)) {
        obj = mapper.readValue(str, List.class);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return Optional.ofNullable(obj);
  }

  @SuppressWarnings("unchecked")
  public static Optional<List<Long>> parseListLongs(@NonNull String str) {
    ObjectMapper mapper = getObjectMapper();
    List<Long> obj = null;
    if (StringHelper.isNonBlank(str)) {
      List<Long> alist = null;
      try {
        alist = mapper.readValue(str, List.class);
      } catch (JsonProcessingException e) {
        // e.printStackTrace();
      }

      obj = ParamMapper.toListLong(alist);
    }

    return Optional.ofNullable(obj);
  }

  public static String stringifyListLongs(@NonNull List<Long> list) {
    return Optional.of(list)
        .map(longs -> longs.stream().map(Object::toString).collect(Collectors.joining(",")))
        .map(inner -> "[" + inner + "]")
        .orElse("");
  }

  public static String stringifyListLongs2(@NonNull List<Long> list) {
    return Optional.of(list)
        .map(
            longs -> longs.stream().sorted().map(Object::toString).collect(Collectors.joining(",")))
        .map(inner -> "[" + inner + "]")
        .orElse("");
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    mapper.setDateFormat(dateFormat);
    return mapper;
  }
}
