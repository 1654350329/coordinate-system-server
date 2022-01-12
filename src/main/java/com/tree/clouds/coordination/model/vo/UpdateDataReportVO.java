package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateDataReportVO {
    @ApiModelProperty(value = "主键")
    private String reportId;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

    @ApiModelProperty(value = "认定工伤决定书编号")
    private String identifiedNumber;

    @ApiModelProperty(value = "认定对象名称")
    private String identifiedName;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "身份证")
    private String idCart;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "类型(1 工 2病)")
    private int sort;

    @ApiModelProperty(value = "科别")
    private String category;

    @ApiModelProperty(value = "伤残病时间(年月日)")
    private String sickTime;

    @ApiModelProperty(value = "伤残情况")
    private String sickCondition;

    @ApiModelProperty(value = "收件时间(年月日)")
    private String receivingTime;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "文件")
    private List<FileInfoVO> bizFile;

}
