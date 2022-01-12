package com.tree.clouds.coordination.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 资料上报
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("data_report")
@ApiModel(value = "DataReport对象", description = "资料上报")
public class DataReport extends BaseEntity implements Serializable {

    public static final String REPORT_ID = "REPORT_ID";
    public static final String TASK_TYPE = "TASK_TYPE";
    public static final String IDENTIFIED_NUMBER = "IDENTIFIED_NUMBER";
    public static final String IDENTIFIED_NAME = "IDENTIFIED_NAME";
    public static final String SEX = "SEX";
    public static final String ID_CART = "ID_CART";
    public static final String UNIT_NAME = "UNIT_NAME";
    public static final String SORT = "SORT";
    public static final String CATEGORY = "CATEGORY";
    public static final String SICK_TIME = "SICK_TIME";
    public static final String SICK_CONDITION = "SICK_CONDITION";
    public static final String RECEIVING_TIME = "RECEIVING_TIME";
    public static final String NATIVE_PLACE = "NATIVE_PLACE";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ADDRESS = "ADDRESS";
    public static final String EXAMINE_STATUS = "EXAMINE_STATUS";
    public static final String EXAMINE_PROGRESS = "EXAMINE_PROGRESS";
    public static final String EXAMINE_DESCRIBE = "EXAMINE_DESCRIBE";
    public static final String EXAMINE_TIME = "EXAMINE_TIME";
    public static final String EXAMINE_USER = "EXAMINE_USER";
    //审核进度 0初始 1上报 2初审 3鉴定 4鉴定复核一 5鉴定复核二 6认定审签
    public static final int EXAMINE_PROGRESS_ZERO = 0;
    public static final int EXAMINE_PROGRESS_ONE = 1;
    public static final int EXAMINE_PROGRESS_TWO = 2;
    public static final int EXAMINE_PROGRESS_THREE = 3;
    public static final int EXAMINE_PROGRESS_FOUR = 4;
    public static final int EXAMINE_PROGRESS_FIVE = 5;
    public static final int EXAMINE_PROGRESS_SIX = 6;
    public static final int EXAMINE_PROGRESS_SEVEN = 7;
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = REPORT_ID, type = IdType.UUID)
    private String reportId;

    @ApiModelProperty(value = "任务类型")
    @TableField(TASK_TYPE)
    private String taskType;

    @ApiModelProperty(value = "认定工伤决定书编号")
    @TableField(IDENTIFIED_NUMBER)
    private String identifiedNumber;

    @ApiModelProperty(value = "认定对象名称")
    @TableField(IDENTIFIED_NAME)
    private String identifiedName;

    @ApiModelProperty(value = "性别")
    @TableField(SEX)
    private String sex;

    @ApiModelProperty(value = "身份证")
    @TableField(ID_CART)
    private String idCart;

    @ApiModelProperty(value = "单位名称")
    @TableField(UNIT_NAME)
    private String unitName;

    @ApiModelProperty(value = "类型(0 工 1病)")
    @TableField(SORT)
    private int sort;

    @ApiModelProperty(value = "科别")
    @TableField(CATEGORY)
    private String category;

    @ApiModelProperty(value = "伤残病时间(年月日)")
    @TableField(SICK_TIME)
    private String sickTime;

    @ApiModelProperty(value = "伤残情况")
    @TableField(SICK_CONDITION)
    private String sickCondition;

    @ApiModelProperty(value = "收件时间(年月日)")
    @TableField(RECEIVING_TIME)
    private String receivingTime;

    @ApiModelProperty(value = "籍贯")
    @TableField(NATIVE_PLACE)
    private String nativePlace;

    @ApiModelProperty(value = "联系方式")
    @TableField(PHONE_NUMBER)
    private String phoneNumber;

    @ApiModelProperty(value = "地址")
    @TableField(ADDRESS)
    private String address;

    @ApiModelProperty(value = "审核进度 0上报 1初审 2待鉴定 3鉴定复核 4认定审签")
    @TableField(EXAMINE_PROGRESS)
    private int examineProgress;

    @ApiModelProperty(value = "审核状态 1失败 0成功")
    @TableField("status")
    private int status;

}
