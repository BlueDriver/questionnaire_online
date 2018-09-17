package com.sp.questionnaire.config.session;


import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-16 Sunday 15:54
 */

public class MySessionContext {

    /**
     * 用于存放登录过的session
     */
    private static   Map<String, HttpSession> sessionMap = new HashMap<>();

    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessionMap.put(session.getId(), session);
        }
    }

    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessionMap.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String sessionID) {
        if (sessionID == null) {
            return null;
        }
        return sessionMap.get(sessionID);
    }


}
