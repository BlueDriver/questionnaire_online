package com.sp.questionnaire.entity.view;

import lombok.*;
import lombok.experimental.Accessors;
import net.sf.json.JSONArray;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-20 Thursday 15:52
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswer{
    @NotNull(message = "问题id不能为空")
    private String id;
    @Range(min = 1, max = 3, message = "问题类型错误")
    private Integer questionType;
    @NotNull(message = "答案不能为空")
    private JSONArray answerContent;
}
