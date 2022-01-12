package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EvaluationSheetDetailVO {

    @NotBlank(message = "评估主键不许为空")
    @ApiModelProperty(value = "评估主键")
    private String evaluationId;

    @ApiModelProperty(value = "专家id")
    private String userId;

    @ApiModelProperty(value = "是否组长 0否 1是")
    private int groupLeader;

    @ApiModelProperty(value = "参与状态 0不参与 1参与")
    private int participationStatus;

    @ApiModelProperty(value = "移除原因")
    private String remark;
}
