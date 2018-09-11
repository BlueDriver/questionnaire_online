package com.sp.demo_sb.service;

import com.sp.demo_sb.entity.Student;

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
