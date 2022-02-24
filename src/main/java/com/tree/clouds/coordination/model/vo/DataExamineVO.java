package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DataExamineVO {
    @ApiModelProperty(value = "资料初审主键")
    @NotBlank(message = "资料初审主键不许为空")
    private String dataExamineId;

    @ApiModelProperty(value = "审核状态 0失败 1成功")
    @NotNull(message = "审核状态不许为空")
    private int status;

    @ApiModelProperty(value = "审核描述")
    private String examineDescribe;

    @ApiModelProperty(value = "资料上报时间")
    @NotBlank(message = "资料上报时间不许为空")
    private String updateTime;
}
