package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DataExamineVOS {

    @NotNull(message = "审核任务不许为空")
    @ApiModelProperty(value = "审核任务")
    @Valid
    private List<DataExamineVO> dataExamineVOS;
}
