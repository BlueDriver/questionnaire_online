package com.sp.questionnaire.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-21 星期六 16:01
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        log.error("exception handler: ", e);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", e.getMessage());
        return map;
    }

    @ExceptionHandler(value = IllegalAccessException.class)
    @ResponseBody
    private Map<String, Object> tokenExpired(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        log.warn("token exception: ", e);
        Map<String, Object> map = new HashMap<>();
        map.put("code", -1);
        map.put("msg", e.getMessage());
        return map;
    }
}
