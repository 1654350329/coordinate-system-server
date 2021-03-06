package com.tree.clouds.coordination.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_manage")
@ApiModel(value = "UserManage对象", description = "用户管理")
@ColumnWidth(20)
public class UserManage extends BaseEntity {

    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String UNIT = "UNIT";
    public static final String SEX = "SEX";
    public static final String JOB = "JOB";
    public static final String TITLE_GRADE = "TITLE_GRADE";
    public static final String ACCOUNT = "ACCOUNT";
    public static final String PASSWORD = "PASSWORD";
    public static final String PASSWORD_TIME = "PASSWORD_TIME";
    public static final String WECHAT_AUTHORIZATION = "WECHAT_AUTHORIZATION";
    public static final String RECEIVE_NOTICE_STATUS = "RECEIVE_NOTICE_STATUS";
    public static final String ACCOUNT_STATUS = "ACCOUNT_STATUS";
    public static final String REMARK = "REMARK";
    public static final String AREA_NAME = "area_name";
    @ExcelIgnore
    @ApiModelProperty(value = "主键")
    @TableId(value = USER_ID, type = IdType.UUID)
    private String userId;

    @ExcelProperty("姓名")
    @ApiModelProperty(value = "姓名")
    @TableField(USER_NAME)
    @NotBlank(message = "姓名不能为空")
    private String userName;

    @ExcelProperty("联系方式")
    @ApiModelProperty(value = "联系方式")
    @TableField(PHONE_NUMBER)
    @NotBlank(message = "姓名不能为空")
    private String phoneNumber;

    @ApiModelProperty(value = "科别")
    @ExcelProperty("科别")
//    @TableField("CATEGORY")
    private String category;

    @ExcelProperty("工作单位")
    @ApiModelProperty(value = "工作单位")
    @NotBlank(message = "工作单位不能为空")
    @TableField(UNIT)
    private String unit;

    @ExcelProperty("所属地区")
    @ApiModelProperty(value = "所属地区")
    @NotBlank(message = "所属地区不能为空")
    @TableField(AREA_NAME)
    private String areaName;

    @ExcelProperty(value = "性别")
    @ApiModelProperty(value = "性别 中文 男 女")
    @NotBlank(message = "性别不能为空")
    @TableField(SEX)
    private String sex;

    @ExcelProperty("职务")
    @ApiModelProperty(value = "职务")
//    @NotBlank(message = "职务不能为空")
    @TableField(JOB)
    private String job;

    @ExcelProperty("职称")
    @ApiModelProperty(value = "职称")
//    @NotBlank(message = "职称不能为空")
    @TableField(TITLE_GRADE)
    private String titleGrade;

    @ExcelProperty("账号")
    @ApiModelProperty(value = "账号")
//    @NotBlank(message = "账号不能为空")
    @TableField(ACCOUNT)
    private String account;

    @ExcelIgnore
    @ApiModelProperty(value = "密码")
    @TableField(PASSWORD)
//    @NotBlank(message = "密码不能为空")
    private String password;

    @ExcelIgnore
    @ApiModelProperty(value = "密码")
    @TableField(PASSWORD_TIME)
//    @NotBlank(message = "密码不能为空")
    private String passwordTime;

    @ExcelIgnore
    @ApiModelProperty(value = "微信授权")
    @TableField(WECHAT_AUTHORIZATION)
//    @NotBlank(message = "微信授权不能为空")
    private String wechatAuthorization;

    @ExcelIgnore
    @ApiModelProperty(value = "是否收取通知 0否 1是")
//    @NotNull(message = "是否收取通知不能为空")
    @TableField(RECEIVE_NOTICE_STATUS)
    private Integer receiveNoticeStatus;

    @ExcelProperty("账号状态 0可用 1不可用")
    @ApiModelProperty(value = "账号状态 0可用 1不可用")
    @NotNull(message = "账号状态不能为空")
    @TableField(ACCOUNT_STATUS)
    private Integer accountStatus;

    @ExcelProperty("备注")
    @ApiModelProperty(value = "备注")
    @TableField(REMARK)
    private String remark;


}
