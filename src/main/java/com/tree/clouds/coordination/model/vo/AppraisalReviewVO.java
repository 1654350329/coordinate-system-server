package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AppraisalReviewVO {

    @NotBlank(message = "复核id不能为空")
    @ApiModelProperty(value = "复核id")
    private String appraiseResultId;

    @NotBlank(message = "鉴定复核状态不能为空")
    @ApiModelProperty(value = "鉴定复核状态(0 待复核 1 一核 2 二核)")
    private String appraisalReviewStatus;

    @NotBlank(message = "复核结果不能为空")
    @ApiModelProperty(value = "复核结果 0反驳 1同意")
    private int appraisalReviewResult;

    @NotBlank(message = "复核意见不能为空")
    @ApiModelProperty(value = "复核意见")
    private String remark;

    @NotBlank(message = "复核时间不能为空")
    @ApiModelProperty(value = "复核时间")
    private String appraisalReviewTime;


}
