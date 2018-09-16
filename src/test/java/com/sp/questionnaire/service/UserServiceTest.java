package com.sp.questionnaire.service;

import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.utils.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.junit.Assert.*;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-15 Saturday 12:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private CommonUtils commonUtils;
    @Test
    public void queryUserByID() {

    }

    @Test
    public void insertUser() throws UnsupportedEncodingException {
        User user = new User();
        user.setId(commonUtils.getUUID())
                .setUsername("Bob")
                .setPassword(commonUtils.encodeByMd5("123"))
                .setEmail("456@qq.com")
                .setCreateTime(new Date())
                .setLastLoginTime(null)
                .setStatus(0)
                .setRandomCode(commonUtils.getUUID());
        System.out.println(userService.insertUser(user));
    }

    @Test
    public void updateUser() {
    }

    @Test
    public void queryUserByEmail() {
        String email = "1123@qq.com";
        User u = userService.queryUserByEmail(email);
        System.out.println(u);
    }
}