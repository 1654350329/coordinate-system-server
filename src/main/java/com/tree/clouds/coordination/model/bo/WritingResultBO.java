package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "WritingResultBO", description = "结论书")
public class WritingResultBO extends DataReport {
    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "审签时间")
    private String reviewTime;

    @ApiModelProperty(value = "认定编号")
    private String appraiseNumber;
    @ApiModelProperty(value = "结论书状态是否存在")
    private String fileId;

}
