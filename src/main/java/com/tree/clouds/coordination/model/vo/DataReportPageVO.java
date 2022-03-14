package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataReportPageVO extends PageParam {
    @ApiModelProperty(value = "认定对象名称")
    private String identifiedName;

    @ApiModelProperty(value = "科别")
    private String category;

    @ApiModelProperty(value = "类别 0工 1病")
    private String sort;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "伤残病开始时间")
    private String sickTimeStart;

    @ApiModelProperty(value = "伤残病结束时间")
    private String sickTimeDnd;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

    @ApiModelProperty(value = "审核进度 0初始 1上报 2初审 3鉴定 4鉴定复核一 5鉴定复核二 6认定审签 7送达 8待鉴定")
    private Integer examineProgress;

    @ApiModelProperty(value = "审核状态 0未审核 1审核 2全部")
    private Integer examineStatus;

    @ApiModelProperty(value = "上报状态 0正常 1驳回")
    private Integer status;

    @ApiModelProperty(value = "收件开始时间")
    private String receivingTimeStart;

    @ApiModelProperty(value = "收件结束时间")
    private String receivingTimeEnd;

    @ApiModelProperty(value = "上报开始时间")
    private String reportTimeStart;

    @ApiModelProperty(value = "上报结束时间")
    private String reportTimeEnd;


}
