package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AppraisalReviewVO {

    @NotBlank(message = "复核id不能为空")
    @ApiModelProperty(value = "复核id")
    private String appraiseResultId;

    @NotNull(message = "复核结果不能为空")
    @ApiModelProperty(value = "复核结果 0反驳 1同意")
    private int appraisalReviewResult;

    @ApiModelProperty(value = "退回位置 0初审 1专家组鉴定")
    private int returnPosition;

    //    @NotBlank(message = "复核意见不能为空")
    @ApiModelProperty(value = "复核意见")
    private String remark;

    @NotBlank(message = "复核时间不能为空")
    @ApiModelProperty(value = "复核时间")
    private String appraisalReviewTime;


}
