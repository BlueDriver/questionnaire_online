package com.sp.questionnaire.service.impl;

import com.sp.questionnaire.service.StudentService;
import com.sp.questionnaire.dao.StudentDao;
import com.sp.questionnaire.entity.Student;
import com.sp.questionnaire.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-21 星期六 11:09
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> queryStudent() {
        return studentDao.queryStudent();
    }

    @Override
    public Student queryStudentByID(String id) {
        return studentDao.queryStudentByID(id);
    }


    //@Transactional(rollbackFor = Exception.class)
    @Transactional  //default is RuntimeException
    @Override
    public boolean insertStudent(Student student) {
        if (student != null && !"".equals(student.getStdNumber())) {
            student.setBirth(new Date());
            try{
                int i = studentDao.insertStudent(student);
                if(i == 1){
                    return true;
                }else {
                    throw new RuntimeException("插入失败！" + student);
                }
            }catch (Exception e){
                throw new RuntimeException("插入失败：" + e.getMessage());
            }
        }else{
            throw new RuntimeException("学号不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean updateStudent(Student student) {
        if (student != null && !"".equals(student.getStdNumber())) {
            try{
                int i = studentDao.updateStudent(student);
                if (i == 1) {
                    return true;
                }else{
                    throw new RuntimeException("修改失败！" + student);
                }
            }catch (Exception e){
                throw new RuntimeException("修改失败：" + e.getMessage());
            }
        }else{
            throw new RuntimeException("修改失败，学号不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean deleteStudent(String id) {
        if (id != null && !"".equals(id)) {
            try{
                int i = studentDao.deleteStudent(id);
                //System.out.println(i);
                if (i == 1) {
                    return true;
                }else{
                    throw new RuntimeException("a删除失败！" + id);
                }
            }catch (Exception e){
                throw new RuntimeException("b删除失败：" + e.getMessage());
            }
        }else{
            throw new RuntimeException("c删除失败，学号不能为空！");
        }
    }
}
