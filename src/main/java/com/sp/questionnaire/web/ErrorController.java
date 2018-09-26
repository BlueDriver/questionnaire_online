package com.sp.questionnaire.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-13 Thursday 14:30
 */


/**
 * 错误处理页
 */
@Controller
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {
    @Override
    public String getErrorPath() {
        //System.out.println("error page");
        return "error";
    }
}
