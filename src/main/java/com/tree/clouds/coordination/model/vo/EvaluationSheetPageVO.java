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

    @ApiModelProperty(value = "类别0工 1病")
    private Integer sort;

    @ApiModelProperty(value = "抽签开始时间")
    private String drawTimeStart;
    @ApiModelProperty(value = "抽签结束时间")
    private String drawTimeEnd;

    @ApiModelProperty(value = "抽签状态 0未抽 1第一轮 2第二轮")
    private Integer drawStatus;

    @ApiModelProperty(value = "发布状态 0未发布 1已发布")
    private Integer releaseStatus;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

}
