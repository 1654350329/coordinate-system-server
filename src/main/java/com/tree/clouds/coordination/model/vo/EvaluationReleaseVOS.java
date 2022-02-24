package com.tree.clouds.coordination.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EvaluationReleaseVOS {
    @NotNull(message = "发布评估不许为空")
    private List<EvaluationReleaseVO> evaluationReleaseVOS;
}
