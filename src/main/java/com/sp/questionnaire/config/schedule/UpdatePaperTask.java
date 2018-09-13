package com.sp.questionnaire.config.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-13 Thursday 16:22
 */
@Slf4j
@Component
@Async
//@EnableScheduling
public class UpdatePaperTask {

    static int i;
    /**
     * 每隔N毫秒执行一次
     */
    @Scheduled(fixedRate = 1000)
    public void scheduled1() {
        System.out.println(new Date() + "\t" +  Thread.currentThread().getName() + "\t" +
        Thread.currentThread().getId());
        i++;
        System.out.println(i);

    }
//    @Scheduled(fixedDelay = 5000)
//    public void scheduled2() {
//        System.out.println();
//    }
}
