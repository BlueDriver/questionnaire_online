package com.sp.questionnaire.web;

import com.sp.questionnaire.config.session.MySessionContext;
import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.entity.view.AddPaperViewPaper;
import com.sp.questionnaire.entity.view.AddPaperViewQuestion;
import com.sp.questionnaire.entity.view.PaperMethodHelp;
import com.sp.questionnaire.utils.FileConvertToPaper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-21 Friday 09:37
 */
@RestController
public class UploadController {
    @Autowired
    private FileConvertToPaper convert;

    @Autowired
    private PaperMethodHelp paperMethodHelp;

    @PostMapping("/api/v1/admin/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws ParseException {
        Map<String, Object> map = new HashMap<>();

//        User user = new User();
//        user.setId("1");

        User user = (User) request.getAttribute("admin");
        if (user == null) {
            map.put("code", -1);
            map.put("msg", "账号未登录或登录已经失效");
            return map;
        }
        if (file.isEmpty()) {
            map.put("code", 2);
            map.put("msg", "未选择文件！");
            return map;
        }
        if (!file.getContentType().equalsIgnoreCase("application/vnd.ms-excel") ||
                !file.getOriginalFilename().endsWith(".xls")) {
            map.put("code", 2);
            map.put("msg", "文件类型不支持！");
            return map;
        }
        if (file.getSize() > 100 * 1024) {  //file cannot large than 100KB
            map.put("code", 2);
            map.put("msg", "文件大小限制在100KB以内！");
            return map;
        }

        AddPaperViewPaper paper = convert.convert(file);
        if (paper != null) {
            map = paperMethodHelp.insertPaper(paper, user.getId(), null);
        }else{
            map.put("code", 2);
            map.put("msg", "文件转换失败，请注意格式要求！");
        }

        return map;
    }
}
