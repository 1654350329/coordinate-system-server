package com.tree.clouds.coordination.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WritingListBO {

    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "年")
    private Integer year;

    @ApiModelProperty(value = "月")
    private Integer month;

    @ApiModelProperty(value = "时间周期")
    private String cycleTime;

    @ApiModelProperty(value = "鉴定人数")
    private int evaluationNumber;
    @ApiModelProperty(value = "附件状态")
    private String uploadStatus;
    @ApiModelProperty(value = "附件路径")
    private String uploadPath;
}
