package com.sp.questionnaire.dao;

import com.sp.questionnaire.entity.Student;
import com.sp.questionnaire.utils.CommonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;


/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-21 星期六 09:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CommonUtils commonUtils;

    @Test
    public void aa() throws UnsupportedEncodingException {
        System.out.println(commonUtils.getUUID());
        System.out.println(commonUtils.encodeByMd5("123"));
    }
    @Test
    public void queryStudent() {
        List<Student> list = studentDao.queryStudent();
        Assert.assertEquals(2, list.size());
        System.out.println(list);
    }

    @Test
    public void queryStudentByID() {
        Student student = studentDao.queryStudentByID("0101");
        System.out.println(student);
    }

    @Test
    public void insertStudent() {
        Student student = new Student();
        student.setStdNumber("0508");
        student.setName("Seven");
        student.setAge(22);
        student.setBirth(new Date());
        int i = studentDao.insertStudent(student);
        System.out.println(i);
    }

    @Test
    public void updateStudent() {
        Student student = new Student();
        student.setStdNumber("0101");
        student.setName("好看");
        student.setBirth(new Date());
        student.setAge(20);
        Assert.assertEquals(1, studentDao.updateStudent(student));
    }

    @Test
    public void deleteStudent() {
        int i = studentDao.deleteStudent("0102");
        System.out.println(i);
        Assert.assertEquals(1, i);
    }
}