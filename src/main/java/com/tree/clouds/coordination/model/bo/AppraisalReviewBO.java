package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppraisalReviewBO extends DataReport {
    @ApiModelProperty(value = "复核id")
    private String appraiseResultId;
    @ApiModelProperty(value = "行文批号")
    private String writingBatchId;
    @ApiModelProperty(value = "认定编号")
    private String appralseNumber;
    @ApiModelProperty(value = "鉴定复核状态(0 待复核 1 一核 2 二核)")
    private String appraisalReviewStatus;

    @ApiModelProperty(value = "复核结果")
    private String appraisalReviewResult;

}
