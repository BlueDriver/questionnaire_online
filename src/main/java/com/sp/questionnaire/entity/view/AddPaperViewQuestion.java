package com.sp.questionnaire.entity.view;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * description:添加Paper所定义的中间Question类
 * Author:Xiaowanwan
 * Date:2018/9/16-17:55
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AddPaperViewQuestion
{
    private Integer questionType;
    private String questionTitle;
    private ArrayList<String> questionOption;

}
