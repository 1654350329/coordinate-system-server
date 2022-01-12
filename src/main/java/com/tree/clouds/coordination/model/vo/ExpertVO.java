package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExpertVO {
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
}
