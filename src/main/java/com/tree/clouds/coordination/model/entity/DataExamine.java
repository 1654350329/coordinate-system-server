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
 * 资料初审
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("data_examine")
@ApiModel(value = "DataExamine对象", description = "资料初审")
public class DataExamine extends BaseEntity {

    public static final String DATA_EXAMINE_ID = "Data_examine_ID";
    public static final String DATA_REPORT_ID = "data_report_id";
    public static final String EXAMINE_STATUS = "examine_status";
    public static final String EXAMINE_DESCRIBE = "examine_describe";
    public static final String EXAMINE_TIME = "examine_time";
    public static final String EXAMINE_USER = "examine_user";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = "Data_examine_ID", type = IdType.UUID)
    private String dataExamineId;

    @ApiModelProperty(value = "资料上报主键")
    @TableField("report_id")
    private String reportId;

    @ApiModelProperty(value = "审核状态")
    @TableField("examine_status")
    private int examineStatus;

    @ApiModelProperty(value = "审核描述")
    @TableField("examine_describe")
    private String examineDescribe;

    @ApiModelProperty(value = "审核时间")
    @TableField("examine_time")
    private String examineTime;

    @ApiModelProperty(value = "审核人")
    @TableField("examine_user")
    private String examineUser;


}
