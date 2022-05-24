package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class writingListUserVO {
    @ApiModelProperty("审核人")
    private String examineUser;
    @ApiModelProperty("经办人")
    private String reportUser;
    @ApiModelProperty("复核人")
    private String appraisalReviewUserTwo;
}
