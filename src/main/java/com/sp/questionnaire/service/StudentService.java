package com.sp.questionnaire.service;


import com.sp.questionnaire.entity.Student;

import java.util.List;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-21 星期六 11:07
 */
public interface StudentService {
    List<Student> queryStudent();

    Student queryStudentByID(String id);

    boolean insertStudent(Student student);

    boolean updateStudent(Student student);

    boolean deleteStudent(String id);
}
