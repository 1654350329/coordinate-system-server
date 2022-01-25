package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DrawVO extends PageParam {
    @NotBlank(message = "行文批次号不许为空")
    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "抽签数量")
    @NotNull(message = "抽签数量不许为空")
    private Integer number;

    @NotNull(message = "行文批次号不许为空")
    @ApiModelProperty(value = "抽签专家类型(1 参评专家 2备选专家)")
    private Integer expertType;

}
