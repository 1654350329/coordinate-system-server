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
public class Appraise extends BaseEntity implements Serializable {

    public static final String APPRAISE_ID = "APPRAISE_ID";
    public static final String WRITING_BATCH_ID = "WRITING_BATCH_ID";
    public static final String APPRAISE_TIME = "APPRAISE_TIME";
    public static final String APPRALSE_STATUS = "APPRALSE_STATUS";
    public static final String APPRAISE_GRADE = "APPRAISE_GRADE";
    public static final String GRADING_PRINCIPLE = "GRADING_PRINCIPLE";
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
    @TableField("appralse_number")
    private String appralseNumber;

    @ApiModelProperty(value = "上报主键")
    @TableField(value = REPORT_ID)
    private String reportId;

    @ApiModelProperty(value = "鉴定状态")
    @TableField(APPRALSE_STATUS)
    private String appralseStatus;

    @ApiModelProperty(value = "鉴定时间")
    @TableField("APPRAISE_TIME")
    private String appraiseTime;

    @ApiModelProperty(value = "等级")
    @TableField("APPRAISE_GRADE")
    private String appraiseGrade;

    @ApiModelProperty(value = "等级原则")
    @TableField("GRADING_PRINCIPLE")
    private String gradingPrinciple;

    @ApiModelProperty(value = "最终等级(结论)")
    @TableField("APPRAISE_RESULT")
    private String appraiseResult;


}
