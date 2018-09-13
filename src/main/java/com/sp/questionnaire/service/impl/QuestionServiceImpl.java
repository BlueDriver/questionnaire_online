package com.sp.questionnaire.service.impl;

import com.sp.questionnaire.dao.QuestionDao;
import com.sp.questionnaire.entity.Question;
import com.sp.questionnaire.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-16:19
 */
@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionDao questionDao;
    @Override
    public List<Question> queryQuestion() {
        return questionDao.queryQuestion();
    }

    @Override
    public List<Question> queryQusetionByPaperId(String paperId) {
        return questionDao.queryQusetionByPaperId(paperId);
    }

    @Override
    public Question queryQuestionById(String id) {
        return questionDao.queryQuestionById(id);
    }

    @Transactional
    @Override
    public boolean insertQuestion(Question question) {
        if (question != null && !"".equals(question.getId())) {
            try{
                int i = questionDao.insertQuestion(question);
                if(i == 1){
                    return true;
                }else {
                    throw new RuntimeException("a:插入问题失败！" + question);
                }
            }catch (Exception e){
                throw new RuntimeException("b:插入问题失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:插入问题失败，问题id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean updateQuestion(Question question) {
        if (question != null && !"".equals(question.getId())) {
            try{
                int i = questionDao.updateQuestion(question);
                if(i == 1){
                    return true;
                }else {
                    throw new RuntimeException("a:更新问题失败！" + question);
                }
            }catch (Exception e){
                throw new RuntimeException("b:更新问题失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:更新问题失败，问题id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean deleteQuestion(String id) {
        if (id != null && !"".equals(id)) {
            try{
                int i = questionDao.deleteQuestion(id);
                if(i == 1){
                    return true;
                }else {
                    throw new RuntimeException("a:删除问题失败！" + id);
                }
            }catch (Exception e){
                throw new RuntimeException("b:删除问题失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:删除问题失败，问题id不能为空！");
        }
    }
}
