package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppraisePageVO extends PageParam {

    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定编号")
    private String appralseNumber;

    @ApiModelProperty(value = "科別")
    private String category;

    @ApiModelProperty(value = "类别1工 2病")
    private Integer sort;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;

    @ApiModelProperty(value = "任务类型")
    private String taskType;
}
