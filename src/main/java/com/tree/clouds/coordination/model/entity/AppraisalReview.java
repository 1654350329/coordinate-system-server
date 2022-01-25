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
 * 复核表
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("appraisal_review")
@ApiModel(value = "AppraisalReview对象", description = "复核表")
public class AppraisalReview extends BaseEntity implements Serializable {

    public static final String APPRAISE_RESULT_ID = "APPRAISE_RESULT_ID";
    public static final String WRITING_BATCH_ID = "WRITING_BATCH_ID";
    public static final String APPRAISAL_REVIEW_STATUS = "APPRAISAL_REVIEW_STATUS";
    public static final String APPRAISAL_REVIEW_RESULT = "APPRAISAL_REVIEW_RESULT";
    public static final String APPRAISAL_REVIEW_TIME = "APPRAISAL_REVIEW_TIME";
    public static final String APPRAISAL_REVIEW_USER = "APPRAISAL_REVIEW_USER";
    public static final String APPRAISAL_REVIEW_USER_TWO = "APPRAISAL_REVIEW_USER_TWO";
    public static final String REPORT_ID = "REPORT_ID";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "复核id")
    @TableId(value = APPRAISE_RESULT_ID, type = IdType.UUID)
    private String appraiseResultId;

    @ApiModelProperty(value = "行文批号")
    @TableField(WRITING_BATCH_ID)
    private String writingBatchId;

    @ApiModelProperty(value = "认定编号")
    @TableField("appralse_number")
    private String appralseNumber;

    @ApiModelProperty(value = "主键")
    @TableField(value = REPORT_ID)
    private String reportId;

    @ApiModelProperty(value = "鉴定复核状态(0 待复核 1 一核 2 二核)")
    @TableField(APPRAISAL_REVIEW_STATUS)
    private Integer appraisalReviewStatus;

    @ApiModelProperty(value = "复核意见")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "二核结果")
    @TableField("APPRAISAL_REVIEW_RESULT_TWO")
    private Integer appraisalReviewResultTwo;

    @ApiModelProperty(value = "一核结果")
    @TableField("APPRAISAL_REVIEW_RESULT_ONE")
    private Integer appraisalReviewResultOne;

    @ApiModelProperty(value = "二核时间")
    @TableField("APPRAISAL_REVIEW_TIME_TWO")
    private String appraisalReviewTimeTwo;

    @ApiModelProperty(value = "一核时间")
    @TableField("APPRAISAL_REVIEW_TIME_ONE")
    private String appraisalReviewTimeOne;

    @ApiModelProperty(value = "复核人")
    @TableField("APPRAISAL_REVIEW_USER_ONE")
    private String appraisalReviewUserOne;

    @ApiModelProperty(value = "复核人2")
    @TableField(APPRAISAL_REVIEW_USER_TWO)
    private String appraisalReviewUserTwo;


}
