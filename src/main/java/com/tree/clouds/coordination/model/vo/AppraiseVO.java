package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AppraiseVO {
    @NotBlank(message = "鉴定id不能为空")
    @ApiModelProperty(value = "鉴定id")
    private String appraiseId;

    @NotBlank(message = "鉴定时间不能为空")
    @ApiModelProperty(value = "鉴定时间")
    private String appraiseTime;

    @NotBlank(message = "鉴定标准不能为空")
    @ApiModelProperty(value = "鉴定标准")
    private String appraiseGrade;

    //    @NotBlank(message = "依据原则不能为空")
    @ApiModelProperty(value = "依据原则")
    private String appraisePrinciple;

    @NotNull(message = "伤残病情况不能为空")
    @ApiModelProperty(value = "伤残病情况")
    private String sickCondition;

    @NotNull(message = "目前伤残情况")
    @ApiModelProperty(value = "专家目前伤残情况")
    private String resultSickCondition;

    @NotBlank(message = "最终等级(结论)不能为空")
    @ApiModelProperty(value = "最终等级(结论)")
    private String appraiseResult;

    @NotNull(message = "文件不能为空")
    @ApiModelProperty(value = "文件")
    private List<FileInfoVO> fileInfoVO;
}
