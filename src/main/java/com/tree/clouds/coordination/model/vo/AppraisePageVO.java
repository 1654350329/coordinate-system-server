package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AppraisePageVO extends PageParam {

    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定对象")
    private String identifiedName;

    @ApiModelProperty(value = "科別")
    private String category;

    @ApiModelProperty(value = "类别1工 2病")
    private Integer sort;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;

    @ApiModelProperty(value = "鉴定状态")
    private String appraiseStatus;

    @ApiModelProperty(value = "性别")
    private String sex;


}
