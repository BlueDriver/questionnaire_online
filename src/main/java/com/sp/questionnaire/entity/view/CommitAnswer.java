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
public class CommitAnswer {
    private String id;
    private int questionType;
    private ArrayList<String> answerContent;
}
