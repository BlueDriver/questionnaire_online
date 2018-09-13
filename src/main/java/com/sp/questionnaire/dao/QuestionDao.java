package com.sp.demo_sb.dao;

import com.sp.demo_sb.entity.Question;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-16:07
 */
public interface QuestionDao {
    /**
     * 查询所有的Question,返回List<Question>
     * @return
     */
    public List<Question> queryQuestion();

    /**
     * 根据试卷的ID查询该试卷所有的Question
     * @param paperId
     * @return
     */
    public List<Question> queryQusetionByPaperId(String paperId);

    /**
     * 根据id查询Question
     * @param id
     * @return
     */
    public Question queryQuestionById(String id);

    /**
     * 插入一个Question
     * @param question
     * @return
     */
    public int insertQuestion(Question question);

    /**
     * 根据id更新该Question
     * @param question
     * @return
     */
    public int updateQuestion(Question question);

    /**
     * 根据id删除该Question
     * @param id
     * @return
     */
    public int deleteQuestion(String id);
}
