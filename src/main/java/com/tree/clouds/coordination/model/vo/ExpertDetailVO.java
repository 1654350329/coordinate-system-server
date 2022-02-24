package com.tree.clouds.coordination.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpertDetailVO {
    @ApiModelProperty(value = "专家主键")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "科别")
    private String category;

    @ApiModelProperty(value = "专家类型(1 参评专家 2备选专家)")
    private Integer expertType;

    @ApiModelProperty(value = "是否组长")
    private int groupLeader;

    @ApiModelProperty(value = "参与状态 0不参与 1参与")
    private int participationStatus;

    @ApiModelProperty(value = "移除原因")
    private String remark;

    @ExcelProperty("工作单位")
    @ApiModelProperty(value = "工作单位")
    private String unit;

    @ApiModelProperty(value = "职务")
    private String job;

    @ApiModelProperty(value = "职称")
    private String titleGrade;

    @ApiModelProperty(value = "抽签时间")
    private String drawTime;
}
