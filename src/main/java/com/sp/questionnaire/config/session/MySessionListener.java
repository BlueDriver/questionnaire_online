package com.sp.questionnaire.config.session;



import javax.servlet.annotation.WebListener;

import javax.servlet.http.*;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-16 Sunday 16:01
 */
@WebListener
public class MySessionListener implements HttpSessionListener, HttpSessionAttributeListener {


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //System.err.println("session add: " + httpSessionEvent.getSession().getId() + new Date());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //System.err.println("session destroy: " + httpSessionEvent.getSession().getId() + new Date());
        MySessionContext.delSession(httpSessionEvent.getSession());
    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (httpSessionBindingEvent.getName().equalsIgnoreCase("admin")) {
            MySessionContext.addSession(httpSessionBindingEvent.getSession());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (httpSessionBindingEvent.getName().equalsIgnoreCase("admin")) {
            MySessionContext.delSession(httpSessionBindingEvent.getSession());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
