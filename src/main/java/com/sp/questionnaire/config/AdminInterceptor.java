package com.sp.questionnaire.config;

import com.sp.questionnaire.config.session.MySessionContext;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import jdk.nashorn.internal.parser.Token;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

/*

        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "36000");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

*/
        System.out.println("pre: " + request.getSession().getId());

        //System.out.println("hd origin: " + request.getHeader("Origin"));


        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Allow-Headers"));
        response.setHeader("Access-Control-Allow-Headers", "token,Accept,Origin,XRequestedWith,Content-Type,LastModified");


        //controller方法调用之前
        String url = request.getRequestURI();
        System.out.println("admin preHandler: " + url);

        String tokenHeader = request.getHeader("token");

        if (url.indexOf("admin") >= 0) {
            //登录成功后将uesr信息存入session，以验证是否登录
            if (tokenHeader != null) {
                HttpSession session = MySessionContext.getSession(tokenHeader);
                if (session != null && session.getAttribute("admin") != null) {
                    request.setAttribute("session", session);
                    return true;
                }
            } else {
                //TODO
                //System.out.println(request.getServletPath());
                //System.out.println(request.getLocalAddr() + request.getContextPath() + "/test");
                //request.getRequestDispatcher("/test").forward(request,response);
                //throw new IllegalAccessException("token is expired or not login");
            }
            JSONObject json = new JSONObject();
            json.put("code", -1);
            json.put("msg", "token expired or not login");
            response.setContentType("application/json");
            response.getWriter().write(json.toString());
            return false;
        } else {
            return true;
        }
    }

}

