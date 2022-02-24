package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DrawInfoVO extends PageParam {
    @NotBlank(message = "行文批次号不许为空")
    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "抽签专家类型(1 参评专家 2备选专家) 空 默认查所有")
    private Integer expertType;
}
