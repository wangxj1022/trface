package com.trendcote.web.common;

import com.trendcote.web.dto.page.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 通用 Api Controller 全局异常处理
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Json defultExcepitonHandler(HttpServletRequest request, Exception e) {
        logger.error("全局异常-->{}");
        Json j = new Json ();
        j.setSuccess(false);
        j.setMsg("系统异常,请您稍后尝试！");
        return j;
    }

}
