package com.sp.questionnaire.web;


import com.sp.questionnaire.entity.Paper;
import com.sp.questionnaire.entity.Question;
import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.entity.view.*;
import com.sp.questionnaire.service.PaperService;
import com.sp.questionnaire.service.QuestionService;
import com.sp.questionnaire.utils.CommonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private PaperMethodHelp paperMethodHelp;

    //获取问卷列表
    @ResponseBody
    @RequestMapping(value = "api/v1/admin/paper-lists", method = RequestMethod.GET)
    public Map<String, Object> paperLists(HttpServletRequest request) throws ParseException {
        Map<String, Object> map = new HashMap<>();

//        User u = new User();
//        u.setId("1");
//        request.getSession().setAttribute("admin", u);
//        request.setAttribute("session", request.getSession());

        User user = (User) request.getAttribute("admin");
        String userId = user.getId();

        //Cookie[] tokens = request.getCookies();

        List<Paper> list = paperService.queryPaperByUserID(userId);
        ArrayList<PaperQueryView> queryViewlist = new ArrayList<PaperQueryView>();

        JSONArray jsonArray = new JSONArray();

        for (Paper p : list) {//遍历list，把Paper转换成PaperQueryView类型
            PaperQueryView paperQueryView = new PaperQueryView();
            paperQueryView.setId(p.getId())
                    .setTitle(p.getTitle())
                    .setStatus(p.getStatus())
                    .setCreateTime(commonUtils.getLongByDate(p.getCreateTime()))
                    .setStartTime(commonUtils.getDateStringByDate(p.getStartTime()))
                    .setEndTime(commonUtils.getDateStringByDate(p.getEndTime()));
            queryViewlist.add(paperQueryView);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", p.getId());
            jsonObject.put("title", p.getTitle());
            jsonObject.put("status", p.getStatus());
            jsonObject.put("createTime", commonUtils.getLongByDate(p.getCreateTime()));
            jsonObject.put("startTime", commonUtils.getDateStringByDate(p.getStartTime()));
            jsonObject.put("endTime", commonUtils.getDateStringByDate(p.getEndTime()));
            jsonArray.add(jsonObject);

        }

        //JSONObject jsonObject = new JSONObject();

        //jsonArray.addAll(queryViewlist);

        /*for (int i = 0, n = jsonArray.size(); i < n; i++) {
            System.out.println(jsonArray.getJSONObject(i));
        }*/

        map.put("code", 0);
        map.put("msg", "ok");
        map.put("data", jsonArray);
        return map;
    }

    //查看问卷
    @ResponseBody
    @RequestMapping(value = "/api/v1/admin/view-paper", method = RequestMethod.POST)
    public Map<String, Object> viewPaper(HttpServletRequest request, @RequestBody String id) throws ParseException {
//        User u = new User();
//        u.setId("1");
//        request.getSession().setAttribute("admin", u);
//        request.setAttribute("session", request.getSession());

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
                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("id",id);
                jsonObject.put("id", id);
                jsonObject.put("title", p.getTitle());
                jsonObject.put("status", p.getStatus());
                jsonObject.put("createTime", commonUtils.getLongByDate(p.getCreateTime()));
                jsonObject.put("startTime", commonUtils.getDateStringByDate(p.getStartTime()));
                jsonObject.put("endTime", commonUtils.getDateStringByDate(p.getEndTime()));


                //查出该试卷的问题
                List<Question> list = questionService.queryQusetionByPaperId(id);

                ArrayList<ViewPaperQuestion> viewPaperQuestionArrayList = new ArrayList<>();


                for (Question q : list) {
                    //转换question，变成ViewPaperQuestion对象
                    ViewPaperQuestion viewPaperQuestion = new ViewPaperQuestion();
                    viewPaperQuestion.setId(q.getId());
                    viewPaperQuestion.setQuestionType(q.getQuestionType());
                    viewPaperQuestion.setQuestionTitle(q.getQuestionTitle());
                    viewPaperQuestion.setQuestionOption(JSONArray.fromObject(q.getQuestionOption()));

                    viewPaperQuestionArrayList.add(viewPaperQuestion);
                }
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(viewPaperQuestionArrayList);
                jsonObject.put("questions", viewPaperQuestionArrayList);
                map.put("data", jsonObject);
            }

        }
        return map;
    }

    /**
     * // 增加问卷
     *
     * @ResponseBody
     * @RequestMapping(value = "/api/v1/add-paper", method = RequestMethod.POST)
     * public Map<String, Object> addPaper(HttpServletRequest request, @RequestBody AddPaperViewPaper paper) throws ParseException {
     * <p>
     * <p>
     * User user = (User) request.getAttribute("admin");
     * <p>
     * //no need id for add new paper
     * String id = null;
     * PaperMethodHelp paperMethodHelp = new PaperMethodHelp();
     * //return updatePaper((request,  paper));
     * return paperMethodHelp.insertPaper(paper, user.getId(), id);
     * <p>
     * }
     */
    //修改问卷 & 新增问卷
    @ResponseBody
    @RequestMapping(value = "/api/v1/admin/update-paper", method = RequestMethod.POST)
    public Map<String, Object> updatePaper(HttpServletRequest request, @RequestBody UpdatePaperViewPaper paper) throws ParseException {

//        User u = new User();
//        u.setId("1");
//        request.getSession().setAttribute("admin", u);
//        request.setAttribute("session", request.getSession());
        User user = (User) request.getAttribute("admin");

        Map<String, Object> map = new HashMap<>();
        //转换addPaper
        AddPaperViewPaper addPaperViewPaper = new AddPaperViewPaper();
        addPaperViewPaper.setTitle(paper.getTitle());
        addPaperViewPaper.setEndTime(paper.getEndTime());
        addPaperViewPaper.setQuestions(paper.getQuestions());
        addPaperViewPaper.setStartTime(paper.getStartTime());
        addPaperViewPaper.setStatus(paper.getStatus());

        //删除Paper
        if (paper.getId() == null) {
            //插入代码
            map = paperMethodHelp.insertPaper(addPaperViewPaper, user.getId(), null);
            if ((int) map.get("code") == 0) {
                map.put("data", 0);
            }
            return map;
        } else {//更新
            HttpSession session = (HttpSession) request.getAttribute("session");
            if (session.getAttribute("admin") != null) {
                //通过检测，准备添加数据
                if (paperService.updatePaperQuestions(paper, user.getId(), addPaperViewPaper)) {
                    map.put("code", 0);
                    map.put("msg", "ok");
                    map.put("data", 0);
                    return map;
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
    @RequestMapping(value = "api/v1/paper-data", method = RequestMethod.POST)
    public Map<String, Object> dataPaper(HttpServletRequest request, @RequestBody String id) throws ParseException {

        User u = new User();
        u.setId("1");
        request.getSession().setAttribute("admin", u);
        request.setAttribute("session", request.getSession());


        JSONObject json = JSONObject.fromObject(id);
        id = json.getString("id");
        //System.out.println("---------------" + id + "---------------------");
        Map<String, Object> map = new HashMap<>();
        if (id == null) {
            map.put("code", 2);
            map.put("msg", "要查看data试卷的id不能为空");
            return map;
        } else {
            if (paperService.dataPaper(id) == null) {
                map.put("code", 2);
                map.put("msg", "要查看data试卷的id无效");
                return map;
            } else {
                //DataPaperViewPaper dataPaperViewPaper = (DataPaperViewPaper) paperService.dataPaper(id);
                JSONObject jsonObject = (JSONObject) paperService.dataPaper(id);
                map.put("code", 0);
                map.put("msg", "ok");
                map.put("data", jsonObject);
                return map;
            }
        }

    }


}