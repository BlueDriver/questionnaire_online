package com.sp.questionnaire.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-13 Thursday 14:06
 */

/**
 * 拦截器
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
        //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行，主要是用于进行资源清理工作
        //System.out.println("admin afterCompletion");
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub
        //请求处理之后进行调用，但是在视图被渲染之前，即Controller方法调用之后
        //System.out.println("admin postHandler");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        // TODO Auto-generated method stub
        //controller方法调用之前
        System.out.println("admin preHandler");
        String url = request.getRequestURI();

        if (url.indexOf("admin") >= 0) {
            //登录成功后将uesr信息存入session，以验证是否登录
            if(request.getSession().getAttribute("user") != null){
                return true;
            }else{
                //TODO
                //System.out.println(request.getServletPath());
                //System.out.println(request.getLocalAddr() + request.getContextPath() + "/test");
                request.getRequestDispatcher("/test").forward(request,response);
                return false;
            }
        }else{
            return true;
        }
    }

}