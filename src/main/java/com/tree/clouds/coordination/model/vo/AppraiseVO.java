package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AppraiseVO {
    @NotBlank(message = "鉴定id不能为空")
    @ApiModelProperty(value = "鉴定id")
    private String appraiseId;

    @NotBlank(message = "鉴定时间不能为空")
    @ApiModelProperty(value = "鉴定时间")
    private String appraiseTime;

    @NotBlank(message = "等级不能为空")
    @ApiModelProperty(value = "等级")
    private String appraiseGrade;

    @NotBlank(message = "等级原则不能为空")
    @ApiModelProperty(value = "等级原则")
    private String gradingPrinciple;

    @NotBlank(message = "最终等级(结论)不能为空")
    @ApiModelProperty(value = "最终等级(结论)")
    private String appraiseResult;

    @NotBlank(message = "文件不能为空")
    @ApiModelProperty(value = "文件")
    private FileInfoVO fileInfoVO;
}
