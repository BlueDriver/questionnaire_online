package com.sp.questionnaire.entity;


import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-07-20 星期五 23:40
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String stdNumber;
    private String name;
    private Integer age;
    private Date birth;

}
