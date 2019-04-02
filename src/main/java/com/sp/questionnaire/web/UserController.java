package com.sp.questionnaire.web;


import com.sp.questionnaire.entity.Answer;
import com.sp.questionnaire.entity.Paper;
import com.sp.questionnaire.entity.Question;
import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.entity.view.PaperAnswer;
import com.sp.questionnaire.entity.view.QuestionAnswer;
import com.sp.questionnaire.entity.view.ViewPaperQuestion;
import com.sp.questionnaire.service.AnswerService;
import com.sp.questionnaire.service.PaperService;
import com.sp.questionnaire.service.QuestionService;
import com.sp.questionnaire.service.UserService;
import com.sp.questionnaire.utils.CommonUtils;
import com.sp.questionnaire.utils.MailUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.*;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-15 Saturday 09:43
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private PaperService paperService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;


    /**
     * <P>注册接口 </p>
     *
     * @param user： 映射的实体对象，result：参数校验的结果对象
     * @return JSON字符串
     */
    @ResponseBody//加这个表示返回的是纯文本数据
    @RequestMapping(value = "/api/v1/register", method = RequestMethod.POST)
    public Map<String, Object> register(HttpServletRequest request, @Valid @RequestBody User user, BindingResult result) throws UnsupportedEncodingException, MessagingException {
        //System.out.println("register: " + request.getSession().getId());
        //System.out.println(user);
        Map<String, Object> map = new HashMap<>();
        if (result.hasErrors()) {
            FieldError error = result.getFieldErrors().get(0);//获得第第一个错误
            map.put("msg", error.getDefaultMessage());//将错误信息放入msg
            map.put("code", 2);
            return map;
        }
        //user校验合法
        User user0 = userService.queryUserByEmail(user.getEmail());//看看邮箱有没有被用过
        if (user0 == null) {  //new user
            user.setId(commonUtils.getUUID())
                    .setPassword(commonUtils.encodeByMd5(user.getPassword()))
                    .setCreateTime(new Date())
                    .setLastLoginTime(null)
                    .setStatus(0)
                    .setRandomCode(commonUtils.getUUID());
            if (userService.insertUser(user)) {   //insert user success
                //发送激活邮件
                mailUtils.sendActivateMail(user.getEmail(), user.getUsername(), user.getRandomCode());
                map.put("code", 0);
                map.put("msg", "ok");
                map.put("data", 0);
            } else {
                map.put("code", 1);
                map.put("msg", "insert database fail");
            }
        } else {    // user exists
            if (user0.getStatus() == 0) {//user not activate
                user.setId(user0.getId())
                        .setPassword(commonUtils.encodeByMd5(user.getPassword()))
                        .setCreateTime(user0.getCreateTime())
                        .setLastLoginTime(null)
                        .setStatus(0)
                        .setRandomCode(commonUtils.getUUID());
                if (userService.updateUser(user)) {
                    mailUtils.sendActivateMail(user.getEmail(), user.getUsername(), user.getRandomCode());
                    map.put("code", 0);
                    map.put("msg", "ok");
                    map.put("data", 2);
                } else {
                    map.put("code", 1);
                    map.put("msg", "update database fail");
                }
            } else if (user0.getStatus() == 1) {    //user is active
                map.put("code", 0);
                map.put("msg", "ok");
                map.put("data", 1);
            } else {
                map.put("code", 1);
                map.put("msg", "error data");
            }
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/login")
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody User user, BindingResult result) throws UnsupportedEncodingException {
        //System.out.println("login: " + request.getSession().getId());
        Map<String, Object> map = new HashMap<>();
        //System.out.println(user.getEmail());
        //登录只需检验email和password
        if (result.hasErrors()) {
            if (user.getUsername() == null) {//username can be null
                //no action
            } else {
                if (user.getUsername().length() >= 2 || user.getUsername().length() <= 64) {
                    //no action
                } else {
                    FieldError error = result.getFieldErrors().get(0);//获得第第一个错误
                    map.put("msg", error.getDefaultMessage());//将错误信息放入msg
                    map.put("code", 2);
                    return map;
                }
            }
        }
        //校验完毕
        User user0 = userService.queryUserByEmail(user.getEmail());
        if (user0 == null) {    // no this user
            map.put("code", 0);
            map.put("msg", "user is not exists");
            JSONObject json = new JSONObject();
            json.put("result", 2);
            map.put("data", json);
        } else {
            if (user0.getPassword().equals(commonUtils.encodeByMd5(user.getPassword()))) {//password is right
                map.put("code", 0);
                JSONObject json = new JSONObject();
                if (user0.getStatus() == 1) {   //is activate
                    //Cookie cookie = new Cookie("USERID", request.getSession().getId());
                    //response.addCookie(cookie);
                    response.setHeader("token", request.getSession().getId());
                    map.put("msg", "ok");
                    json.put("result", 0);
                    json.put("token", request.getSession().getId());
                    json.put("username", user0.getUsername());
                    json.put("email", user0.getEmail());
                    //登录成功，将user存入session中
                    request.getSession().setAttribute("admin", user0);
                    //update user last login time
                    user0.setLastLoginTime(new Date());
                    userService.updateUser(user0);
                } else {
                    json.put("result", 3);
                    map.put("msg", "email is not activate");
                }
                map.put("data", json);
            } else {  //error password
                map.put("code", 0);
                map.put("msg", "password error");
                JSONObject json = new JSONObject();
                json.put("result", 1);
                map.put("data", json);
            }
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/admin/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        //System.out.println("logout: ");
        HttpSession session = (HttpSession) request.getAttribute("session");
        //System.out.println(session.getId());
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("admin") != null) {
            request.removeAttribute("admin");
            map.put("code", 0);
            map.put("msg", "ok");
            map.put("data", 0);
        } else {
            map.put("code", 0);
            map.put("msg", "not login or timeout");
            map.put("data", 1);
        }
        //request.getSession().invalidate();//使当前session无效
        return map;
    }

    //不加ResponseBody表示该请求对应的是一个页面
    @RequestMapping(value = "/api/v1/activate/{code}", method = RequestMethod.GET)
    public String activate(@PathVariable("code") String code) {
        //System.out.println(code);
        if (code == null || code.length() < 10) {
            return "invalid";
        }
        User user0 = userService.queryUserByRandomCode(code);
        //System.out.println(user0);
        if (user0 != null) {

            if (user0.getStatus() == 0) {//user is not activate
                user0.setStatus(1);
                if (userService.updateUser(user0)) {
                    return "success";
                } else {
                    return "fail";
                }
            } else if (user0.getStatus() == 1) {    //user has activate
                return "already";
            } else {
                return "invalid";
            }
        } else {  //invalid
            return "invalid";
        }
    }

    /**
     * 用户答卷
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/api/v1/user/view-paper", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> userViewPaper(String id) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        if (id == null) {
            map.put("code", 2);
            map.put("msg", "问卷id不能为空");
        } else {
            Paper p = paperService.queryPaperByID(id);
            if (p == null) {
                map.put("code", 2);
                map.put("msg", "问卷id无效");
            } else {
                //check time for update the status
                if (p.getStatus() == 0 || p.getStatus() == 1) {
                    if (new Date().after(p.getEndTime())) { //need be over
                        if (paperService.updatePaper(p.setStatus(2))) {

                        }
                        p.setStatus(2);
                    } else if (new Date().after(p.getStartTime())) {    //need be start
                        if (paperService.updatePaper(p.setStatus(1))) {

                        }
                        p.setStatus(1);
                    }
                }
                int status = p.getStatus();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", status);
                if (status == 0) {  // not start
                    map.put("code", 0);
                    map.put("msg", "该问卷还未开始");
                } else if (status == 1) {   //ing
                    if (new Date().before(p.getStartTime())) {//已经发布，但是未到开始时间
                        map.put("code", 0);
                        map.put("msg", "该问卷还未开始");
                        jsonObject.put("status", 4);
                        jsonObject.put("title", p.getTitle());
                        jsonObject.put("startTime", commonUtils.getDateStringByDate(p.getStartTime()));
                        jsonObject.put("endTime", commonUtils.getDateStringByDate(p.getEndTime()));
                        map.put("data", jsonObject);
                        return map;
                    }
                    map.put("code", 0);
                    map.put("msg", "ok");
                    jsonObject.put("id", id);
                    jsonObject.put("title", p.getTitle());
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

                } else if (status == 2) {   //expired
                    map.put("code", 0);
                    map.put("msg", "问卷已结束");
                    jsonObject.put("title", p.getTitle());
                    jsonObject.put("startTime", commonUtils.getDateStringByDate(p.getStartTime()));
                    jsonObject.put("endTime", commonUtils.getDateStringByDate(p.getEndTime()));
                } else if (status == 3) {   //deleted
                    map.put("code", 0);
                    map.put("msg", "该问卷已被删除");
                } else {  //unknown status
                    map.put("code", 0);
                    map.put("msg", "未知的问卷状态");
                }
                map.put("data", jsonObject);
            }

        }
        return map;

    }

    /**
     * 用户提交问卷答案
     *
     * @param answer
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/v1/user/commit-paper", method = RequestMethod.POST)
    public Map<String, Object> userViewPaper(@Valid @RequestBody PaperAnswer answer, BindingResult result) {
        Map<String, Object> map = new HashMap<>();
        if (result.hasErrors()) {
            FieldError error = result.getFieldErrors().get(0);//获得第第一个错误
            map.put("msg", error.getDefaultMessage());//将错误信息放入msg
            map.put("code", 2);
            return map;
        }
        String paperId = answer.getId();
        Paper paper = paperService.queryPaperByID(paperId);
        if (paper != null) {
            List<Answer> ansList = new ArrayList<>();
            for (QuestionAnswer qa : answer.getAnswers()) {
                Answer ans = new Answer();
                ans.setId(commonUtils.getUUID())
                        .setPaperId(paperId)
                        .setQuestionId(qa.getId())
                        .setQuestionType(qa.getQuestionType())
                        .setCreateTime(new Date());
                JSONArray array = JSONArray.fromObject(qa.getAnswerContent());
                ans.setAnswerContent(array.toString());
                ansList.add(ans);
            }
            if (answerService.insertAnswerList(ansList)) {
                map.put("code", 0);
                map.put("msg", "ok");
            }
        } else {
            map.put("code", 2);
            map.put("msg", "问卷id无效");
        }
        return map;
    }

}
