package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AppraisalReviewBO对象", description = "复核分页")
public class AppraisalReviewBO extends DataReport {
    @ApiModelProperty(value = "复核id")
    private String appraiseResultId;
    @ApiModelProperty(value = "行文批号")
    private String writingBatchId;
    @ApiModelProperty(value = "认定编号")
    private String appraiseNumber;
    @ApiModelProperty(value = "鉴定复核状态(0 待复核 1 一核 2 二核)")
    private Integer appraisalReviewStatus;

    @ApiModelProperty(value = "复核结果 0驳回 1通过")
    private Integer appraisalReviewResult;

    @ApiModelProperty(value = "复核意见")
    private String remark;

}
