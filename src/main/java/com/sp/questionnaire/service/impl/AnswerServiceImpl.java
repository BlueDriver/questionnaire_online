package com.sp.demo_sb.service.impl;

import com.sp.demo_sb.dao.AnswerDao;
import com.sp.demo_sb.entity.Answer;
import com.sp.demo_sb.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-18:31
 */
@Service
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    private AnswerDao answerDao;
    @Override
    public List<Answer> queryAnswer() {
        return answerDao.queryAnswer();
    }

    @Override
    public List<Answer> queryAnswerByPaperId(String paperId) {
        return answerDao.queryAnswerByPaperId(paperId);
    }

    @Override
    public List<Answer> queryAnswerByQuestionId(String questionId) {
        return answerDao.queryAnswerByQuestionId(questionId);
    }

    @Override
    public Answer queryAnswerById(String id) {
        return answerDao.queryAnswerById(id);
    }

    @Transactional
    @Override
    public boolean insertAnswer(Answer answer) {
        if (answer !=null && !"".equals(answer.getId())){
            try{
                int i = answerDao.insertAnswer(answer);
                if (i ==1){
                    return true;
                }else {
                    throw new RuntimeException("a:插入答案失败！" + answer);
                }
            }catch (Exception e){
                throw new RuntimeException("b:插入答案失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:插入答案失败，Answer的id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean updateAnswer(Answer answer) {
        if (answer !=null && !"".equals(answer.getId())){
            try{
                int i = answerDao.updateAnswer(answer);
                if (i ==1){
                    return true;
                }else {
                    throw new RuntimeException("a:更新答案失败！" + answer);
                }
            }catch (Exception e){
                throw new RuntimeException("b:更新答案失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:更新答案失败，Answer的id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean deleteAnswer(String id) {
        if (id !=null && !"".equals(id)){
            try{
                int i = answerDao.deleteAnswer(id);
                if (i ==1){
                    return true;
                }else {
                    throw new RuntimeException("a:删除答案失败！" + id);
                }
            }catch (Exception e){
                throw new RuntimeException("b:删除答案失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:删除答案失败，Answer的id不能为空！");
        }
    }
}
