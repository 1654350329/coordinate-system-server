package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CompletionMethod {
    @NotBlank(message = "发送主键不能为空")
    @ApiModelProperty(value = "发送主键")
    private String messageId;

    @NotBlank(message = "送达方式不能为空")
    @ApiModelProperty(value = "送达方式")
    private String type;
}
