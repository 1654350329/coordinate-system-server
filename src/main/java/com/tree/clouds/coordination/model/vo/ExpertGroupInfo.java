package com.tree.clouds.coordination.model.vo;

import com.tree.clouds.coordination.model.entity.UserManage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 专家组信息
 */
@Data
public class ExpertGroupInfo extends UserManage {
    @ApiModelProperty(value = "是否组长")
    private int groupLeader;
}
