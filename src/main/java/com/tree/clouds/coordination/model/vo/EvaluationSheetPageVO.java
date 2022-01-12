package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaluationSheetPageVO extends PageParam {

    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "批次操作人")
    private String createUser;

    @ApiModelProperty(value = "专家名称")
    private String userName;

    @ApiModelProperty(value = "科別")
    private String category;

    @ApiModelProperty(value = "类别1工 2病")
    private Integer sort;

    @ApiModelProperty(value = "抽签开始时间")
    private String drawTimeStart;
    @ApiModelProperty(value = "抽签结束时间")
    private String drawTimeEnd;

    @ApiModelProperty(value = "抽签状态 0未抽 1已抽")
    private int drawStatus;

    //暂定不合理
    @ApiModelProperty(value = "任务类型")
    private String taskType;

}
