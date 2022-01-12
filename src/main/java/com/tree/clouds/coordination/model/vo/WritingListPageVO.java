package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WritingListPageVO extends PageParam {
    @ApiModelProperty(value = "年")
    private Integer year;
    @ApiModelProperty(value = "月")
    private Integer mount;
    @ApiModelProperty(value = "包含认定对象名称")
    private String identifiedName;
    @ApiModelProperty(value = "附件状态 已上传 未上传")
    private Integer uploadStatus;
}
