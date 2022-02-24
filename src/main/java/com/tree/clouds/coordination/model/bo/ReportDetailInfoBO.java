package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 林振坤
 * @description
 * @date 2022/1/3 0003 22:20
 */
@Data
@ApiModel(value = "ReportDetailInfoBO", description = "鉴定详细")
public class ReportDetailInfoBO extends DataReport {
    @ApiModelProperty(value = "认定编号")
    private String appraiseNumber;

    @ApiModelProperty(value = "等级")
    private String appraiseGrade;

    @ApiModelProperty(value = "最终等级(结论)")
    private String appraiseResult;

}
