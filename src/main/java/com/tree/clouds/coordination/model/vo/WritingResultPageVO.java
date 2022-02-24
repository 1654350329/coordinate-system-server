package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WritingResultPageVO extends PageParam {
    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定对象编码")
    private String appraiseNumber;

    @ApiModelProperty(value = "认定对象名称")
    private String identifiedName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

    @ApiModelProperty(value = "结论书状态是否存在")
    private String resultFile;

    @ApiModelProperty(value = "上报开始时间")
    private String reportTimeStart;

    @ApiModelProperty(value = "上报结束时间")
    private String reportTimeEnd;
}
