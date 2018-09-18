package com.sp.questionnaire.service.impl;

import com.sp.questionnaire.dao.QuestionDao;
import com.sp.questionnaire.entity.Question;
import com.sp.questionnaire.service.PaperService;
import com.sp.questionnaire.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/17-16:18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PaperServiceImplTest {


    @Autowired
    private PaperService paperService;
    @Autowired
    private QuestionDao questionDao;

    @Test
    public void dataPaper() throws ParseException {
        //PaperServiceImpl paperServiceImpl = new PaperServiceImpl();
        //String id = "1";
        //paperService.dataPaper(id);
        //System.out.println("hello");
        paperService.dataPaper("1");

        //questionDao.getQuestionsByPaperIdAndQuestionType("1",2);

    }
}