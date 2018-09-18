package com.sp.questionnaire.web;


import com.sp.questionnaire.entity.Paper;
import com.sp.questionnaire.entity.Question;
import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.entity.view.*;
import com.sp.questionnaire.service.PaperService;
import com.sp.questionnaire.service.QuestionService;
import com.sp.questionnaire.utils.CommonUtils;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(value = "api/v1/admin/paper-lists", method = RequestMethod.GET)
    public Map<String, Object> paperLists(HttpServletRequest request) throws ParseException {
        Map<String, Object> map = new HashMap<>();

        /*User u = new User();
        u.setId("1");
        request.getSession().setAttribute("admin", u);
        request.setAttribute("session", request.getSession());*/

        User user = (User) request.getSession().getAttribute("admin");
        String userId = user.getId();

        //Cookie[] tokens = request.getCookies();

        List<Paper> list = paperService.queryPaperByUserID(userId);

        ArrayList<PaperQueryView> queryViewlist = new ArrayList<PaperQueryView>();
        PaperQueryView[] paperQueryView = new PaperQueryView[list.size()];
        for (int i = 0, n = paperQueryView.length; i < n; i++) {
            paperQueryView[i] = new PaperQueryView();
        }
        int k = 0;
        for (Paper p : list) {//遍历list，把Paper转换成PaperQueryView类型
            paperQueryView[k].setId(p.getId())
                    .setTitle(p.getTitle())
                    .setStatus(p.getStatus())
                    .setCreateTime(commonUtils.getDateStringByDate(p.getCreateTime()))
                    .setStartTime(commonUtils.getDateStringByDate(p.getStartTime()))
                    .setEndTime(commonUtils.getDateStringByDate(p.getEndTime()));
            queryViewlist.add(paperQueryView[k]);
            k++;

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

       /* User u = new User();
        u.setId("1");
        request.getSession().setAttribute("admin", u);
        request.setAttribute("session", request.getSession());*/


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

                for (Question q : list) {
                    //转换question，变成ViewPaperQuestion对象
                    ViewPaperQuestion viewPaperQuestion = new ViewPaperQuestion();
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

        /*User u = new User();
        u.setId("1");
        request.getSession().setAttribute("admin", u);
        request.setAttribute("session", request.getSession());*/

        HttpSession session = (HttpSession) request.getAttribute("session");
        //System.out.println("paper:" + paper);

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
        request.setAttribute("session", request.getSession());*/

        //System.out.println(paper.getId());

        Map<String, Object> map = new HashMap<>();
        //转换paper
        AddPaperViewPaper addPaperViewPaper = new AddPaperViewPaper();
        addPaperViewPaper.setTitle(paper.getTitle());
        addPaperViewPaper.setEndTime(paper.getEndTime());
        addPaperViewPaper.setQuestions(paper.getQuestions());
        addPaperViewPaper.setStartTime(paper.getStartTime());
        addPaperViewPaper.setStatus(paper.getStatus());


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
    public Map<String, Object> deletePaper(HttpServletRequest request, @RequestBody Object idList) {
        List<String> listId = new ArrayList<>();
        JSONObject j = JSONObject.fromObject(idList);

        JSONArray array = JSONArray.fromObject(j.get("idList"));

        for (int i = 0; i < array.size(); i++) {
            listId.add((String) array.get(i));
            //System.out.println(array.get(i));
        }

        //System.out.println(j);

        //System.out.println(idList);

        /*User u = new User();
        u.setId("1");
        request.getSession().setAttribute("admin", u);
        request.setAttribute("session", request.getSession());*/

        //JSONObject json = JSONObject.fromObject(idList);

        //Collections.addAll(listId,array);

        Map<String, Object> map = new HashMap<>();
        if (listId.size() <= 0) {
            map.put("code", 2);
            map.put("msg", "要删除试卷的id 不能为空");
            return map;
            //return map;
        } else {
            if (paperService.deleteManyPaper(listId)) {
                map.put("code", 0);
                map.put("msg", "ok");
                return map;
            } else {
                map.put("code", 1);
                map.put("msg", "系统异常");
                return map;
            }
        }
    }


    //查看问卷数据
    @ResponseBody
    @RequestMapping(value = "api/v1/admin/paper-data", method = RequestMethod.POST)
    public Map<String, Object> dataPaper(HttpServletRequest request, @RequestBody String id) throws ParseException {

        /*User u = new User();
        u.setId("1");
        request.getSession().setAttribute("admin", u);
        request.setAttribute("session", request.getSession());*/


        JSONObject json = JSONObject.fromObject(id);
        id = json.getString("id");
        //System.out.println("---------------" + id + "---------------------");
        Map<String, Object> map = new HashMap<>();
        if (id == null) {
            map.put("code", 2);
            map.put("msg", "要查看data试卷的id 不能为空");
            return map;
        } else {
            if (paperService.dataPaper(id) == null) {
                map.put("code", 2);
                map.put("msg", "要查看data试卷的id 错误");
                return map;
            } else {
                DataPaperViewPaper dataPaperViewPaper = (DataPaperViewPaper) paperService.dataPaper(id);
                map.put("code", 0);
                map.put("msg", "ok");
                map.put("data", dataPaperViewPaper);
                return map;
            }
        }


        //return map;


    }


}


