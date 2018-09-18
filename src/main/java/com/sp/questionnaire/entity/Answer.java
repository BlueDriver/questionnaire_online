package com.sp.questionnaire.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-10:43
 */

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private String id;//答案ID
    private String paperId;// 试卷ID，外键
    private String questionId;//问题ID，外键
    private Integer questionType;//问题类型：1：单选题2：多选题3：简答题
    private Date createTime;//答题时间
    private String answerContent;//问题的答案：
    // 1.选择题：答案来自question表的question_option，[option1,option2,option3...]
    // 2.简答题：用户填写的文本

}
