package com.sp.questionnaire.entity.view;

import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import java.util.List;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-20 Thursday 09:08
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaperAnswer {
    @NotNull(message = "问卷id不能为空")
    private String id;
    @NotNull(message = "答案列表不能为空")
    private List<QuestionAnswer> answers;
}
