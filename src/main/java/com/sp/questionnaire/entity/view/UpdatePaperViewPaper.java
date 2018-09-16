package com.sp.questionnaire.entity.view;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/16-19:54
 */

@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaperViewPaper {
    private String id;
    private String title;
    private String startTime;
    private String endTime;
    private Integer status;
    private ArrayList<AddPaperViewQuestion> questions;

}