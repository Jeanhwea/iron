package com.avic.mti.iron.common.http.advice;

import com.avic.mti.iron.common.exception.BadRequestException;
import com.avic.mti.iron.common.exception.NotFoundException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的异常捕获类
 *
 * @author Jinghui Hu
 * @since 2019-12-30, JDK1.8
 */
@ControllerAdvice
public class HttpGlobalAdvice {

  public static final Logger logger = LoggerFactory.getLogger(HttpGlobalAdvice.class);

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public HttpStatusEntity badRequestHandler(HttpServletRequest req, BadRequestException ex) {
    logger.warn("服务器 400 错误: {}", ex.getMessage());
    logger.warn("服务器 400 错误: {}", Arrays.asList(ex.getStackTrace()));
    return new HttpStatusEntity(
        HttpStatus.BAD_REQUEST, req.getRequestURI(), ex.getMessage(), ex.getStackTrace());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public HttpStatusEntity notFoundHandler(HttpServletRequest req, NotFoundException ex) {
    logger.info("服务器 404 错误: {}", ex.getMessage());
    // logger.info("服务器 404 错误: {}", Arrays.asList(ex.getStackTrace()));
    return new HttpStatusEntity(
        HttpStatus.NOT_FOUND, req.getRequestURI(), ex.getMessage(), ex.getStackTrace());
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public HttpStatusEntity defaultHandler(HttpServletRequest req, RuntimeException ex) {
    logger.error("服务器 500 错误: {}", ex.getMessage());
    logger.error("服务器 500 错误: {}", Arrays.asList(ex.getStackTrace()));
    return new HttpStatusEntity(
        HttpStatus.INTERNAL_SERVER_ERROR, req.getRequestURI(), ex.getMessage(), ex.getStackTrace());
  }
}
