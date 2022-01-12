package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EvaluationReleaseVO {
    @NotBlank(message = "行文批次号不许为空")
    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @NotBlank(message = "发布时间不许为空")
    @ApiModelProperty(value = "发布时间")
    private String releaseTime;

    @NotBlank(message = "发布地址不许为空")
    @ApiModelProperty(value = "发布地址")
    private String releaseAddress;

}
