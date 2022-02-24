package com.tree.clouds.coordination.model.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AppraisalReviewVOS {
    @NotNull(message = "鉴定不许为空")
    @Valid
    private List<AppraisalReviewVO> appraisalReviewVOS;
}
