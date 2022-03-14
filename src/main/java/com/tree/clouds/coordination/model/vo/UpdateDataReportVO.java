package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "资料上报详细", description = "资料上报详细")
public class UpdateDataReportVO {
    @ApiModelProperty(value = "主键")
    private String reportId;

    @ApiModelProperty(value = "任务类型")
    @NotBlank(message = "任务类型不能为空")
    private String taskType;

    @ApiModelProperty(value = "认定工伤决定书编号")
    @NotBlank(message = "认定工伤决定书编号不能为空")
    private String identifiedNumber;

    @ApiModelProperty(value = "认定对象名称")
    @NotBlank(message = "认定对象名称不能为空")
    private String identifiedName;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不能为空")
    private String sex;

    @ApiModelProperty(value = "身份证")
    @NotBlank(message = "身份证不能为空")
    private String idCart;
    @ApiModelProperty(value = "出生日期")
    private String birth;

    @ApiModelProperty(value = "单位名称")
//    @NotBlank(message = "单位名称不能为空")
    private String unitName;

    @ApiModelProperty(value = "类型(0 工 1病)")
    @NotNull(message = "类型不能为空")
    private Integer sort;

    @ApiModelProperty(value = "科别")
    @NotBlank(message = "科别不能为空")
    private String category;

    @ApiModelProperty(value = "伤残病时间(年月日)")
    @NotBlank(message = "伤残病时间不能为空")
    private String sickTime;

    @ApiModelProperty(value = "伤残情况")
    @NotBlank(message = "伤残情况不能为空")
    private String sickCondition;

    @ApiModelProperty(value = "收件时间(年月日)")
    private String receivingTime;

    @ApiModelProperty(value = "籍贯")
    @NotBlank(message = "籍贯不能为空")
    private String nativePlace;

    @ApiModelProperty(value = "联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String phoneNumber;

    @ApiModelProperty(value = "地址")
//    @Size(min = 6, max = 30, message = "地址应该在6-30字符之间")
    private String address;

    @ApiModelProperty(value = "文件")
    private List<FileInfoVO> bizFile;

}
