package com.avic.mti.iron.common.http.proxy;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;

public interface Eunuch {

  Map<String, Object> getForMap(String appname, String endpoint);

  Map<String, Object> getForMap(String appname, String endpoint, HttpHeaders headers);

  Map<String, Object> getForMap(String appname, String endpoint, Map<String, Object> params);

  Map<String, Object> getForMap(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  List<Map<String, Object>> getForList(String appname, String endpoint);

  List<Map<String, Object>> getForList(String appname, String endpoint, HttpHeaders headers);

  List<Map<String, Object>> getForList(String appname, String endpoint, Map<String, Object> params);

  List<Map<String, Object>> getForList(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object getForObject(String appname, String endpoint);

  Object getForObject(String appname, String endpoint, HttpHeaders headers);

  Object getForObject(String appname, String endpoint, Map<String, Object> params);

  Object getForObject(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  Map<String, Object> postForMap(String appname, String endpoint);

  Map<String, Object> postForMap(String appname, String endpoint, HttpHeaders headers);

  Map<String, Object> postForMap(String appname, String endpoint, Map<String, Object> params);

  Map<String, Object> postForMap(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  List<Map<String, Object>> postForList(String appname, String endpoint);

  List<Map<String, Object>> postForList(String appname, String endpoint, HttpHeaders headers);

  List<Map<String, Object>> postForList(
      String appname, String endpoint, Map<String, Object> params);

  List<Map<String, Object>> postForList(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object postForObject(String appname, String endpoint);

  Object postForObject(String appname, String endpoint, HttpHeaders headers);

  Object postForObject(String appname, String endpoint, Map<String, Object> params);

  Object postForObject(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  Map<String, Object> putForMap(String appname, String endpoint);

  Map<String, Object> putForMap(String appname, String endpoint, HttpHeaders headers);

  Map<String, Object> putForMap(String appname, String endpoint, Map<String, Object> params);

  Map<String, Object> putForMap(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  List<Map<String, Object>> putForList(String appname, String endpoint);

  List<Map<String, Object>> putForList(String appname, String endpoint, HttpHeaders headers);

  List<Map<String, Object>> putForList(String appname, String endpoint, Map<String, Object> params);

  List<Map<String, Object>> putForList(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);

  Object putForObject(String appname, String endpoint);

  Object putForObject(String appname, String endpoint, HttpHeaders headers);

  Object putForObject(String appname, String endpoint, Map<String, Object> params);

  Object putForObject(
      String appname, String endpoint, HttpHeaders headers, Map<String, Object> params);
}
