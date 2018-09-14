package com.sp.questionnaire;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-20 星期五 22:44
 */
@RestController
@Slf4j
public class Hello {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String hello(){
        log.info("test url");
        return "Hello SpringBoot!";
    }

    @RequestMapping(value = "/test/admin", method = RequestMethod.GET)
    public String testAdmin(){
        return "Hello admin";
    }

    @RequestMapping(value = "nologin")
    public String noLogin(){
        return "you haven't login!";
    }
}
