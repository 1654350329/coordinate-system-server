package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WritingBatchVO extends PageParam {
    @NotBlank(message = "行文批次号不许为空")
    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "专家组参与状态 0不参与 1参与 不传查所有")
    private Integer participationStatus;
}
