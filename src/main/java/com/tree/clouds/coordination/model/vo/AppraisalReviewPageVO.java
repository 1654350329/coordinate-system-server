package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppraisalReviewPageVO extends PageParam {

    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定对象编码")
    private String appralseNumber;

    @ApiModelProperty(value = "认定对象名称")
    private String identifiedName;

    @ApiModelProperty(value = "科別")
    private String category;

    @ApiModelProperty(value = "类别1工 2病")
    private Integer sort;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "复核开始时间")
    private String appraisalReviewTimeStart;
    @ApiModelProperty(value = "复核结束时间")
    private String appraisalReviewTimeEnd;

}
