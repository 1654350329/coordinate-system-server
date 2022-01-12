package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserManageVO {
    @ApiModelProperty(value = "主键")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "微信授权")
    private String wechatAuthorization;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "账号状态")
    private int accountStatus;


}
