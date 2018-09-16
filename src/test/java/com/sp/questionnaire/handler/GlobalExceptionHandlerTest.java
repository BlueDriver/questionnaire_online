package com.sp.questionnaire.handler;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-16 Sunday 09:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GlobalExceptionHandlerTest {
    @Test
    public void testLog() {
        try {
            int i = 0;
            i = i / i;
        } catch (Exception e) {
            System.err.println(e);
            log.error("error", e);
        }
    }
}