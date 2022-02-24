package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppraiseBO extends DataReport {
    @ApiModelProperty(value = "鉴定id")
    private String appraiseId;

    @ApiModelProperty(value = "行文批号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定编号")
    private String appraiseNumber;

    @ApiModelProperty(value = "鉴定状态 0未鉴定 1已鉴定")
    private Integer appraiseStatus;


}
