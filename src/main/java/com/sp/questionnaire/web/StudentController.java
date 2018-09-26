package com.sp.questionnaire.web;

import com.sp.questionnaire.entity.Student;
import com.sp.questionnaire.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-21 星期六 15:09
 */
@RestController
@RequestMapping("/admin")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private Map<String, Object> listStudent(HttpServletRequest request) {
        //System.out.println(request.getSession().getId());
        //System.out.println(request.getRemoteAddr());
        Map<String, Object> map = new HashMap<>();
        map.put("data", studentService.queryStudent());
        return map;
    }

    @RequestMapping(value = "/getstudentbyid", method = RequestMethod.GET)
    private Map<String, Object> getStudentById(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", studentService.queryStudentByID(id));
        return map;
    }

    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    private Map<String, Object> listStudent(@RequestBody Student student) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", studentService.insertStudent(student));
        return map;
    }

    @RequestMapping(value = "/modifystudent", method = RequestMethod.POST)
    private Map<String, Object> modifyStudent(@RequestBody Student student) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", studentService.updateStudent(student));
        return map;
    }

    @RequestMapping(value = "/deletestudent", method = RequestMethod.GET)
    private Map<String, Object> deleteStudent(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", studentService.deleteStudent(id));
        return map;
    }

}
