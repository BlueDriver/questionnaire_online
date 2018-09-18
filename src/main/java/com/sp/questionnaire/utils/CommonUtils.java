package com.sp.questionnaire.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-06-14 星期四 14:50
 */
@Service
@Slf4j
public class CommonUtils {
    static MessageDigest md5 = null;
    static BASE64Encoder base64en = new BASE64Encoder();

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("md5 init error", e);
        }
    }

    /**
     * md5算法编码加密
     *
     * @param sourceString: 需要被加密的文本
     * @return 加密后的文本
     * @throws UnsupportedEncodingException
     */
    public String encodeByMd5(String sourceString) throws UnsupportedEncodingException {
        return base64en.encode(md5.digest(sourceString.getBytes("utf-8")));
    }


    static SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String getFormatDateTimeNow() {
        return sdfFull.format(System.currentTimeMillis());
    }

    /**
     * 格式化时间戳
     *
     * @param timeStamp
     * @return
     */
    public String getFormatDateTimeByTimeStamp(long timeStamp) {
        return sdfFull.format(timeStamp);
    }

    static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * <P>将年月日字符串转成Date </p>
     *
     * @param dateString： 合乎规范的年月日字符串
     * @return Date
     */
    public Date getDateByDateString(String dateString) {
        Date date;
        try {
            date = sdfDate.parse(dateString);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public String getDateStringByDate(Date date) throws ParseException {
        return sdfDate.format(date);
    }

    public Long getLongByDate(Date date) {
        if (date==null)return null;
        return date.getTime();
    }

    /**
     * 生成随机的UUID
     *
     * @return
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * <P>计算两个日期相隔天数 </p>
     *
     * @param before: 起始日期; then: 目标日期
     * @return 整数天
     */
    public int getDifferenceDay(Date before, Date then) {
        Calendar last_c = Calendar.getInstance();
        last_c.setTime(before);
        Calendar now_c = Calendar.getInstance();
        now_c.setTime(then);
        int last_year = last_c.get(Calendar.YEAR);
        int now_year = now_c.get(Calendar.YEAR);
        if (last_year == now_year) {//the same year
            return now_c.get(Calendar.DAY_OF_YEAR) - last_c.get(Calendar.DAY_OF_YEAR);
        } else {
            //different year
            long length = then.getTime() - before.getTime();
            return (int) length / (1000 * 3600 * 24);
        }
    }

    /**
     * 获取request的IP地址
     *
     * @param request
     * @return IP address
     */
    public String getIPAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-real-ip");//先从nginx自定义配置获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
