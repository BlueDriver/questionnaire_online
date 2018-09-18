package com.sp.questionnaire.entity.view;

import com.sp.questionnaire.entity.Paper;
import com.sp.questionnaire.entity.Question;
import com.sp.questionnaire.entity.User;
import com.sp.questionnaire.service.PaperService;
import com.sp.questionnaire.service.QuestionService;
import com.sp.questionnaire.utils.CommonUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/16-20:05
 */
public class PaperMethodHelp {
    public Map<String, Object> insertPaper(AddPaperViewPaper paper, HttpSession session,
                                           CommonUtils commonUtils, PaperService paperService,
                                           QuestionService questionService, String id) throws ParseException {
        Map<java.lang.String, java.lang.Object> map = new HashMap<>();

        if (checkPaper(paper) == null) {
            //放入插入代码
            //id = null;
            //System.out.println(id);
            return insert(paper, session, commonUtils, paperService, questionService, id);
        } else {
            return checkPaper(paper);
        }
    }

    @Transactional
    public Map<String, Object> insert(AddPaperViewPaper paper, HttpSession session,
                                      CommonUtils commonUtils, PaperService paperService,
                                      QuestionService questionService, String id) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        //HttpSession session = (HttpSession) request.getAttribute("session");
        //System.out.println(id);
        if (session.getAttribute("admin") != null) {
            //通过检测，准备添加数据
            //System.out.println("准备添加数据");
            Question question = new Question();
            Paper paper1 = new Paper();
            java.lang.String paper1Id = commonUtils.getUUID();
            if (id == null) {
                paper1.setId(paper1Id);
            } else {
                paper1.setId(id);
            }
            User user = (User) session.getAttribute("admin");
            paper1.setUserId(user.getId());
            paper1.setCreateTime(new Date());
            paper1.setStartTime(commonUtils.getDateByDateString(paper.getStartTime()));
            paper1.setEndTime(commonUtils.getDateByDateString(paper.getEndTime()));
            paper1.setStatus(paper.getStatus());
            paper1.setTitle(paper.getTitle());
            //吧paper1存入数据库
            paperService.insertPaper(paper1);

            //paper添加完成，接下来添加Questions
            for (AddPaperViewQuestion a : paper.getQuestions()) {
                question.setId(commonUtils.getUUID());
                question.setPaperId(paper1.getId());
                question.setCreateTime(new Date());
                question.setQuestionType(a.getQuestionType());
                question.setQuestionTitle(a.getQuestionTitle());
                question.setQuestionOption(a.getQuestionOption().toString());

                System.out.println("options:" + question.getQuestionOption());

                //把question存入数据库
                questionService.insertQuestion(question);
            }

            map.put("code", 0);
            map.put("msg", "ok");
            return map;

        } else {
            map.put("code", -1);
            map.put("msg", "not token");
            return map;
        }
    }

    public Map<String, Object> checkPaper(AddPaperViewPaper paper) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        if (paper.getTitle() == null || "".equals(paper.getTitle())) {
            map.put("code", 2);
            map.put("msg", "试卷题目 不能为空");
            return map;
        } else if (paper.getTitle().length() <= 2 || paper.getTitle().length() >= 64) {
            map.put("code", 2);
            map.put("msg", "试卷题目 应该在2个字符到64个字符之间");
            return map;
        } else if (paper.getStatus() != 0 && paper.getStatus() != 1) {
            map.put("code", 2);
            map.put("msg", "试卷状态 应该是0或者是1");
            return map;
        } else if (paper.getQuestions().size() <= 0) {
            map.put("code", 2);
            map.put("msg", "该试卷没有添加问题");
            return map;
        } else {
            for (AddPaperViewQuestion a : paper.getQuestions()) {
                if (a.getQuestionTitle().length() <= 1 || a.getQuestionTitle().length() >= 128) {
                    map.put("code", 2);
                    map.put("msg", "问题名为" + a.getQuestionTitle() + "的题目 应该是1到128个字符");
                    return map;
                } else if (a.getQuestionType() != 1 && a.getQuestionType() != 2 && a.getQuestionType() != 3) {
                    map.put("code", 2);
                    map.put("msg", "问题名为" + a.getQuestionTitle() + "的问题类型 应该是1，2，3");
                    return map;
                } else if ((a.getQuestionType() == 1 || a.getQuestionType() == 2) && a.getQuestionOption().size() < 2) {
                    map.put("code", 2);
                    map.put("msg", "问题名为" + a.getQuestionTitle() + "的问题选项不足够");
                    return map;
                } else if (a.getQuestionType() == 3 && a.getQuestionOption().size() > 0) {
                    map.put("code", 2);
                    map.put("msg", "问题名为" + a.getQuestionTitle() + "的问题的答案应该为空");
                    return map;
                }
            }
        }
        return null;
    }


}
