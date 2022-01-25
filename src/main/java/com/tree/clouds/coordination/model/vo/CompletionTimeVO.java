package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CompletionTimeVO {
    @NotBlank(message = "发送主键不能为空")
    @ApiModelProperty(value = "发送主键")
    private String messageId;

    @NotBlank(message = "送达时间不能为空")
    @ApiModelProperty(value = "送达时间")
    private String time;

}
