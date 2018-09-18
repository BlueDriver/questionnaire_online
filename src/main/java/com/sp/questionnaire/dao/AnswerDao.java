package com.sp.questionnaire.dao;

import com.sp.questionnaire.entity.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-18:10
 */
public interface AnswerDao {
    /**
     * 查询所有的Answer,返回List<Answer>
     * @return
     */
    public List<Answer> queryAnswer();

    /**
     * 根据试卷Id查询Answer,返回List<Answer>
     * @param paperId
     * @return
     */
    public List<Answer> queryAnswerByPaperId(String paperId);

    /**
     * 根据QuestionId查询Answer,返回List<Answer>
     * @param questionId
     * @return
     */
    public List<Answer> queryAnswerByQuestionId(String questionId);

    /**
     * 根据Id查询Answer,返回Answer对象
     * @param id
     * @return
     */
    public Answer queryAnswerById(String id);

    /**
     * 插入Answer,返回影响行数
     * @param answer
     * @return
     */
    public int insertAnswer(Answer answer);

    /**
     * 更新Answer,返回影响行数
     * @param answer
     * @return
     */
    public int updateAnswer(Answer answer);

    /**
     * 删除Answer,返回影响行数
     * @param id
     * @return
     */
    public int deleteAnswer(String id);

    /**
     * 查询Answer被答次数,返回次数
     * @param paperId,questionId
     * @return
     */
    public int countAnswer(@Param("paperId") String paperId, @Param("questionId") String questionId);

}
