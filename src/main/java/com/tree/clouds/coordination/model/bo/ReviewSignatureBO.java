package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewSignatureBO extends DataReport {
    @ApiModelProperty(value = "主键")
    private String reviewAndSignatureId;
    @ApiModelProperty(value = "行文批号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定编号")
    private String appralseNumber;

    @ApiModelProperty(value = "审签状态(0待审签 1 已审签)")
    private String reviewStatus;

    @ApiModelProperty(value = "审签结果(0反驳 1 同意)")
    private String reviewResult;
}
