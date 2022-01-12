package com.tree.clouds.coordination.model.vo;

import com.tree.clouds.coordination.model.entity.Appraise;
import com.tree.clouds.coordination.model.entity.ReviewSignature;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AppraiseInfoVO {
    @ApiModelProperty(value = "专家组鉴定信息")
    private Appraise appraise;
    @ApiModelProperty(value = "复核信息")
    private List<AppraisalReviewExpertVO> appraisalReviewExpertVOS;
    @ApiModelProperty(value = "认定结论审签信息")
    private ReviewSignature reviewSignature;
}
