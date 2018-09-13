package com.sp.questionnaire.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-10:29
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;//用户ID
    private String username;//用户名（昵称）
    private String password;//MD5加密后的密码
    private String email;// 邮箱
    private Date createTime;//注册时间
    private Date lastLoginTime;//最后登录时间
    private Integer status;//状态值：0：未激活1：已激活
    private String randomCode;//随机码，用于激活用户
}
