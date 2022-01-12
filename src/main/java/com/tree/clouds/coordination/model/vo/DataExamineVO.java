package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataExamineVO {
    @ApiModelProperty(value = "资料初审主键")
    private String dataExamineId;

    @ApiModelProperty(value = "审核状态 0失败 1成功")
    private int examineStatus;

    @ApiModelProperty(value = "审核描述")
    private String examineDescribe;

    @ApiModelProperty(value = "资料上报时间")
    private String updateTime;
}
