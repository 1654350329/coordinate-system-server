package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class JoinEvaluationSheetVO {
    @ApiModelProperty(value = "行文批号", required = true)
    @NotEmpty(message = "行文批号不能为空")
    private String writingBatchId;
    @ApiModelProperty(value = "上报主键", required = true)
    @NotEmpty(message = "上报主键不能为空")
    private List<String> reportIds;
}
