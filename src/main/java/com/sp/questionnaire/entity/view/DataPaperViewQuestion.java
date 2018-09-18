package com.sp.questionnaire.entity.view;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/17-10:39
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DataPaperViewQuestion {
    private String id;
    private Integer questionType;
    private String questionTitle;
    private List<String> questionOption;
    private List<Object> answerContent;
}
