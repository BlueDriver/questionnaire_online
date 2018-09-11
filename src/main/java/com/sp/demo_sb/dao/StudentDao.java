package com.sp.demo_sb.dao;

import com.sp.demo_sb.entity.Student;

import java.util.List;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-21 星期六 09:20
 */

public interface StudentDao {
    List<Student> queryStudent();
    Student queryStudentByID(String id);
    int insertStudent(Student student);
    int updateStudent(Student student);
    int deleteStudent(String id);
}
