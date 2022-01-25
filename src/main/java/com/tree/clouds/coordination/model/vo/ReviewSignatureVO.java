package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReviewSignatureVO {

    @ApiModelProperty(value = "主键")
    private String reviewAndSignatureId;

    @ApiModelProperty(value = "审签时间")
    private String reviewTime;

    @ApiModelProperty(value = "审签结果(0反驳 1 同意)")
    private String reviewResult;

    @ApiModelProperty(value = "审签意见")
    private String remark;

}
