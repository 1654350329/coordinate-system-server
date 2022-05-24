package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppraisalReviewPageVO extends PageParam {

    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定对象编码")
    private String appraiseNumber;

    @ApiModelProperty(value = "认定对象名称")
    private String identifiedName;

    @ApiModelProperty(value = "审签状态(0待审签 1 已审签)")
    private Integer appraisalReviewStatus;

    @ApiModelProperty(value = "审签结果 0驳回  1通过")
    private Integer reviewResult;

    @ApiModelProperty(value = "科別")
    private String category;

    @ApiModelProperty(value = "类别0工 1病")
    private Integer sort;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;


    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "审签开始时间")
    private String appraisalReviewTimeStart;
    @ApiModelProperty(value = "审签结束时间")
    private String appraisalReviewTimeEnd;

}
