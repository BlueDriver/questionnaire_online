package com.sp.questionnaire.entity.view;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/17-10:23
 */

@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DataPaperViewPaper {
    private String id;
    private String title;
    private Integer status;
    private Long createTime;
    private String startTime;
    private String endTime;
    private Integer totalCount;
    private List<DataPaperViewQuestion> questions;

}
