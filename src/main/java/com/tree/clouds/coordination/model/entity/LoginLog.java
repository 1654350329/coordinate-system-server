package com.tree.clouds.coordination.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 登入日志
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("login_log")
@ApiModel(value = "LoginLog对象", description = "登入日志")
public class LoginLog extends BaseEntity {

    public static final String LOGIN_ID = "login_id";
    public static final String USER_ID = "user_id";
    public static final String IP = "ip";
    public static final String STATUS = "status";
    public static final String LONG_TIME = "long_time";
    public static final String ERROR_SORT = "error_sort";
    public static final String ERROR_INFO = "error_info";

    @ApiModelProperty(value = "登入日志id")
    @TableId(value = "login_id", type = IdType.UUID)
    private String loginId;

    @ApiModelProperty(value = "用户账号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "ip地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "登入日志")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "逗留时长")
    @TableField("long_time")
    private String longTime;

    @ApiModelProperty(value = "异常种类")
    @TableField("error_sort")
    private String errorSort;

    @ApiModelProperty(value = "异常信息")
    @TableField("error_info")
    private String errorInfo;


}
