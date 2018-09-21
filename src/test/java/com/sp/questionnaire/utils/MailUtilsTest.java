package com.sp.questionnaire.utils;

import com.sp.questionnaire.entity.Paper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.Date;


/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-15 Saturday 09:09
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailUtilsTest {
    @Autowired
    private MailUtils mailUtils;

    @Test
    public void sendMail() throws MessagingException {
        String receiver = "2943884288@qq.com";
        String nickName = "蓝色司机";
        String code = "1234567";

        mailUtils.sendActivateMail(receiver, nickName,code);
    }

    @Test
    public void sendPaperStartMail() throws MessagingException {
        Paper paper = new Paper();
        paper.setId("1234")
                .setTitle("Hello world")
                .setCreateTime(new Date())
                .setStartTime(new Date())
                .setEndTime(new Date());
        String receiver = "2943884288@qq.com";
        String nickName = "蓝色司机";
        mailUtils.sendPaperStartMail(receiver,nickName,paper);

    }
}