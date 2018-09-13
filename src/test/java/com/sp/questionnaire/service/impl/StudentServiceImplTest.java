package com.sp.questionnaire.service.impl;

import com.sp.questionnaire.entity.Student;
import com.sp.questionnaire.service.StudentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-21 星期六 11:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void queryStudent() {
        List<Student> list = studentService.queryStudent();
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void queryStudentByID() {
        Student student = studentService.queryStudentByID("0508");
        Assert.assertEquals("Seven", student.getName());
    }

    @Test
    public void insertStudent() {
        Student student = new Student();
        student.setStdNumber("0111");
        student.setName("JJ");
        student.setAge(20);
        student.setBirth(new Date());
        Assert.assertEquals(true, studentService.insertStudent(student));
    }

    @Test
    public void updateStudent() {
        Student student = new Student();
        student.setStdNumber("0111");
        student.setName("yy");
        student.setAge(20);
        //student.setBirth(new Date());
        Assert.assertEquals(true, studentService.updateStudent(student));
    }

    @Test
    public void deleteStudent() {
        String id = "0001";
        Assert.assertEquals(true, studentService.deleteStudent(id));

    }
}