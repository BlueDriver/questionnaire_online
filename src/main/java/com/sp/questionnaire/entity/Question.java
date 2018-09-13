package com.sp.questionnaire.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-10:39
 */

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private String id;//问题ID
    private String papperId;//试卷ID，外键
    private Date createTime;// 问题创建时间
    private Integer questionType;//问题类型：1：单选题2：多选题3：简答题
    private String questionTitle;//问题标题
    private String questionOption;// 问题的选项：1.选择题：[option1,option2,option3...]2.简答题：空字符串

}
