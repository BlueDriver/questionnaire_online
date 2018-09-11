package com.sp.demo_sb.handler;

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
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest req, Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("msg", e.getMessage());
        return map;

    }
}
