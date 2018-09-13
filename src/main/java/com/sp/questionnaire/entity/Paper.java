package com.sp.questionnaire.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-10:31
 */

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Paper {
    private String id;//试卷ID
    private String userId;//用户ID，外键
    private String title;//试卷标题
    private Date createTime;//试卷创建时间
    private Integer status;//状态值：0：未发布1：已发布2：已结束
    private Date startTime;//开始时间
    private Date endTime;//结束时间
}
