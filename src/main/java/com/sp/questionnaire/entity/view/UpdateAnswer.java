package com.sp.questionnaire.entity.view;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAnswer {
    private String id;
    private String paper_id;
    private String question_id;
    private int questionType;
    private String create_time;
    private ArrayList<String> answerContent;
}
