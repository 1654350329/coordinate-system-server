package com.tree.clouds.coordination.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 鉴定表
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("appraise")
@ApiModel(value = "Appraise对象", description = "鉴定表")
public class Appraise extends BaseEntity {

    public static final String APPRAISE_ID = "APPRAISE_ID";
    public static final String WRITING_BATCH_ID = "WRITING_BATCH_ID";
    public static final String APPRAISE_TIME = "APPRAISE_TIME";
    public static final String APPRAISE_STATUS = "APPRAISE_STATUS";
    public static final String APPRAISE_GRADE = "APPRAISE_GRADE";
    public static final String APPRAISE_PRINCIPLE = "APPRAISE_PRINCIPLE";
    public static final String APPRAISE_RESULT = "APPRAISE_RESULT";
    public static final String REPORT_ID = "REPORT_ID";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "鉴定id")
    @TableId(value = "APPRAISE_ID", type = IdType.UUID)
    private String appraiseId;

    @ApiModelProperty(value = "行文批次号")
    @TableField("WRITING_BATCH_ID")
    private String writingBatchId;

    @ApiModelProperty(value = "认定编号")
    @TableField("appraise_number")
    private String appraiseNumber;

    @ApiModelProperty(value = "上报主键")
    @TableField(value = REPORT_ID)
    private String reportId;

    @ApiModelProperty(value = "鉴定状态 0未鉴定 1已鉴定 2驳回")
    @TableField(APPRAISE_STATUS)
    private Integer appraiseStatus;

    @ApiModelProperty(value = "鉴定时间")
    @TableField("APPRAISE_TIME")
    private String appraiseTime;

    @ApiModelProperty(value = "鉴定标准")
    @TableField("APPRAISE_GRADE")
    private String appraiseGrade;

    @ApiModelProperty(value = "等级原则")
    @TableField("APPRAISE_PRINCIPLE")
    private String appraisePrinciple;

    @ApiModelProperty(value = "最终等级(结论)")
    @TableField("APPRAISE_RESULT")
    private String appraiseResult;

    @ApiModelProperty(value = "目前伤残情况")
    @TableField("RESULT_SICK_CONDITION")
    private String resultSickCondition;

    @ApiModelProperty(value = "附件")
    @TableField(exist = false)
    private List<String> files;

    @ApiModelProperty(value = "伤残情况")
    @TableField(exist = false)
    private String sickCondition;

}
