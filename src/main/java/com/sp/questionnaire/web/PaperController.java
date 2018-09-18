
package com.sp.questionnaire.web;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sp.questionnaire.entity.Answer;
import com.sp.questionnaire.entity.Paper;
import com.sp.questionnaire.entity.Question;
import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.entity.view.*;
import com.sp.questionnaire.service.AnswerService;
import com.sp.questionnaire.service.PaperService;
import com.sp.questionnaire.service.QuestionService;
import com.sp.questionnaire.utils.CommonUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.DocFlavor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/16-14:05
 */
@Controller
public class PaperController {
    @Autowired
    private PaperService paperService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommonUtils commonUtils;

    //获取问卷列表
    @ResponseBody
    @RequestMapping(value = "api/v1/admin/paper-lists", method = RequestMethod.POST)
    public Map<String, Object> paperLists(HttpServletRequest request) throws ParseException {
        Map<String, Object> map = new HashMap<>();


        User user = (User) request.getSession().getAttribute("admin");
        String userId = user.getId();

        //Cookie[] tokens = request.getCookies();

        List<Paper> list = paperService.queryPaperByUserID(userId);
        ArrayList<PaperQueryView> queryViewlist = new ArrayList<PaperQueryView>();
        PaperQueryView paperQueryView = new PaperQueryView();
        for (Paper p : list) {//遍历list，把Paper转换成PaperQueryView类型
            paperQueryView.setId(p.getId())
                    .setTitle(p.getTitle())
                    .setStatus(p.getStatus())
                    .setCreateTime(commonUtils.getDateStringByDate(p.getCreateTime()))
                    .setStartTime(commonUtils.getDateStringByDate(p.getStartTime()))
                    .setEndTime(commonUtils.getDateStringByDate(p.getEndTime()));
            queryViewlist.add(paperQueryView);

        }
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("data", queryViewlist);
        return map;
    }

    //查看问卷
    @ResponseBody
    @RequestMapping(value = "/api/v1/admin/view-paper", method = RequestMethod.POST)
    public Map<String, Object> viewPaper(HttpServletRequest request, @RequestBody String id) throws ParseException {

        Map<String, Object> map = new HashMap<>();

        JSONObject json = JSONObject.fromObject(id);
        id = json.getString("id");


        if (id == null) {
            map.put("code", 2);
            map.put("msg", "id 不能为空");
        } else {
            Paper p = paperService.queryPaperByID(id);
            if (p == null) {
                map.put("code", 2);
                map.put("msg", "id 不正确");
            } else {
                map.put("code", 0);
                map.put("msg", "ok");
                map.put("id", id);
                map.put("title", p.getTitle());
                map.put("status", p.getStatus());
                map.put("createTime", commonUtils.getLongByDate(p.getCreateTime()));
                map.put("startTime", commonUtils.getDateStringByDate(p.getStartTime()));
                map.put("endTime", commonUtils.getDateStringByDate(p.getEndTime()));


                //查出该试卷的问题
                List<Question> list = questionService.queryQusetionByPaperId(id);

                ArrayList<ViewPaperQuestion> viewPaperQuestionArrayList = new ArrayList<>();

                //转换question，变成ViewPaperQuestion对象
                ViewPaperQuestion viewPaperQuestion = new ViewPaperQuestion();
                for (Question q : list) {
                    viewPaperQuestion.setId(q.getId());
                    viewPaperQuestion.setQuestionType(q.getQuestionType());
                    viewPaperQuestion.setQuestionTitle(q.getQuestionTitle());
                    viewPaperQuestion.setQuestionOption(q.getQuestionOption());

                    viewPaperQuestionArrayList.add(viewPaperQuestion);
                }
                map.put("questions", viewPaperQuestionArrayList);
            }

        }
        return map;
    }

    //增加问卷
    @ResponseBody
    @RequestMapping(value = "/api/v1/admin/add-paper", method = RequestMethod.POST)
    public Map<String, Object> addPaper(HttpServletRequest request, @RequestBody AddPaperViewPaper paper) throws ParseException {
        Map<String, Object> map = new HashMap<>();


        HttpSession session = (HttpSession) request.getAttribute("session");

        String id = null;
        PaperMethodHelp paperMethodHelp = new PaperMethodHelp();


        return paperMethodHelp.insertPaper(paper, session, commonUtils, paperService, questionService, id);

    }


    //修改问卷
    @ResponseBody
    @RequestMapping(value = "/api/v1/admin/update-paper", method = RequestMethod.POST)
    public Map<String, Object> updatePaper(HttpServletRequest request, @RequestBody UpdatePaperViewPaper paper) throws ParseException {

        /*User u = new User();
        u.setId("1");
        request.getSession().setAttribute("admin", u);
        request.setAttribute("session", request.getSession());
*/

        Map<String, Object> map = new HashMap<>();
        //转换paper
        AddPaperViewPaper addPaperViewPaper = new AddPaperViewPaper();
        addPaperViewPaper.setTitle(paper.getTitle());
        addPaperViewPaper.setEndTime(paper.getEndTime());
        addPaperViewPaper.setQuestions(paper.getQuestions());
        addPaperViewPaper.setStartTime(paper.getStartTime());
        addPaperViewPaper.setStatus(paper.getStatus());


        //删除Paper
        if (paper.getId() == null) {
            map.put("code", 2);
            map.put("msg", "要删除试卷的id 不能为空");
            return map;
        } else {
            if (paperService.deletePaper(paper.getId())) {//删除paper

                if (questionService.deleteQuestionsByPaperId(paper.getId())) {//删除成功
                    PaperMethodHelp paperMethodHelp = new PaperMethodHelp();
                    HttpSession session = (HttpSession) request.getAttribute("session");
                    return paperMethodHelp.insertPaper(addPaperViewPaper, session,
                            commonUtils, paperService,
                            questionService, paper.getId());
                }


            }

        }
        map.put("code", 1);
        map.put("msg", "系统异常");
        return map;
    }

    ///delete-paper
    @ResponseBody
    @RequestMapping(value = "api/v1/admin/delete-paper", method = RequestMethod.POST)
    public Map<String, Object> deletePaper(HttpServletRequest request, @RequestBody String id) {
        JSONObject json = JSONObject.fromObject(id);
        id = json.getString("id");
        Map<String, Object> map = new HashMap<>();
        if (id == null) {
            map.put("code", 2);
            map.put("msg", "要删除试卷的id 不能为空");
            return map;
        } else {
            if (paperService.deletePaper(id)) {//删除paper

                if (questionService.deleteQuestionsByPaperId(id)) {
                    map.put("code", 0);
                    map.put("msg", "ok");
                    map.put("data", 0);
                    return map;
                }
            }
            map.put("code", 1);
            map.put("msg", "删除试卷失败 系统异常");
            return map;
        }
        //return map;
    }

    @RequestMapping(value = "api/v1/user/view-paper", method = RequestMethod.GET)
    public Map<String, Object> viewUserPaper( @RequestBody String id) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = JSONObject.fromObject(id);
        id = json.getString("id");
        if (id != null){
            Paper p = paperService.queryPaperByID(id);
            if (p != null){
                if (p.getStatus().equals(0)){
                    map.put("code", 2);
                    map.put("msg", "未发布");
                    return map;
                }
                else if (p.getStatus().equals(2)){
                    map.put("code", 2);
                    map.put("msg", "已结束");
                    return map;
                }
                else if (p.getStatus().equals(3)){
                    map.put("code", 2);
                    map.put("msg", "无此问题");
                    return map;
                }
                else{
                    map.put("code", 0);
                    map.put("msg", "ok");
                    map.put("id", id);
                    map.put("title", p.getTitle());
                    map.put("status", p.getStatus());
                    map.put("createTime", commonUtils.getLongByDate(p.getCreateTime()));
                    map.put("startTime", commonUtils.getDateStringByDate(p.getStartTime()));
                    map.put("endTime", commonUtils.getDateStringByDate(p.getEndTime()));

                    List<Question> list = questionService.queryQusetionByPaperId(id);

                    ArrayList<ViewPaperQuestion> viewPaperQuestionArrayList = new ArrayList<>();

                    //转换question，变成ViewPaperQuestion对象
                    ViewPaperQuestion viewPaperQuestion = new ViewPaperQuestion();
                    for (Question q : list) {
                        viewPaperQuestion.setId(q.getId());
                        viewPaperQuestion.setQuestionType(q.getQuestionType());
                        viewPaperQuestion.setQuestionTitle(q.getQuestionTitle());
                        viewPaperQuestion.setQuestionOption(q.getQuestionOption());

                        viewPaperQuestionArrayList.add(viewPaperQuestion);
                    }
                    map.put("questions", viewPaperQuestionArrayList);
                    return map;
                }

            }
            map.put("code", 2);
            map.put("msg", "id 不正确");
            return map;

        }
        map.put("msg", "id 不能为空");
        map.put("code", 2);
        return map;
    }

    @RequestMapping(value = "api/v1/user/commit-paper", method = RequestMethod.POST)
    public Map<String, Object> commitPaper(@RequestBody CommitAnswer answer) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject json = JSONObject.fromObject(answer);
        String id = json.getString("id");
        int questionType = json.getInt("questionType");
        JSONObject answerContent = json.getJSONObject("answerContent");
        if (id != null){

        }
        map.put("code", 2);
        map.put("msg", "id 不存在");
        // unfinished
        return map;
    }


}