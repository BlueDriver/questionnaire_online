package com.sp.questionnaire.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-13 Thursday 10:50
 */

/**
 * 所有返回页面的controller都写在这，页面放在resources/static/
 */
@Controller
public class StaticPageController {
    /**
     *  <P>返回静态页面 </p>
     *
     *  @param key
     *  @return static目录下的页面名称
     */
    @RequestMapping(value = "/")
    public String index(String key){
        System.out.println(key);
        return "index";
    }

    @RequestMapping(value = "/admin/success")
    public String success(String key){
        System.out.println(key);
        return "success";
    }

    @RequestMapping(value = "/admin/invalid")
    public String invalid(String key){
        System.out.println(key);
        return "success";
    }


}
