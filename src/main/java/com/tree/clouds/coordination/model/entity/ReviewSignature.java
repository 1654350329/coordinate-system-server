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
 * 认定审签
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("review_signature")
@ApiModel(value = "ReviewSignature对象", description = "认定审签")
public class ReviewSignature extends BaseEntity implements Serializable {

    public static final String REVIEW_AND_SIGNATURE_ID = "REVIEW_AND_SIGNATURE_ID";
    public static final String WRITING_BATCH_ID = "WRITING_BATCH_ID";
    public static final String REVIEW_TIME = "REVIEW_TIME";
    public static final String REVIEW_STATUS = "REVIEW_STATUS";
    public static final String REVIEW_RESULT = "REVIEW_RESULT";
    public static final String REVIEW_USER = "REVIEW_USER";
    public static final String RESULT_FILE = "RESULT_FILE";
    public static final String REPORT_ID = "REPORT_ID";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = REVIEW_AND_SIGNATURE_ID, type = IdType.UUID)
    private String reviewAndSignatureId;

    @ApiModelProperty(value = "文书编号")
    @TableField(WRITING_BATCH_ID)
    private String writingBatchId;

    @ApiModelProperty(value = "认定编号")
    @TableField("appralse_number")
    private String appralseNumber;

    @ApiModelProperty(value = "上报主键")
    @TableField(value = REPORT_ID)
    private String reportId;

    @ApiModelProperty(value = "审签时间")
    @TableField(REVIEW_TIME)
    private String reviewTime;

    @ApiModelProperty(value = "审签状态(0待审签 1 已审签)")
    @TableField(REVIEW_STATUS)
    private String reviewStatus;

    @ApiModelProperty(value = "审签结果(0反驳 1 同意)")
    @TableField(REVIEW_RESULT)
    private String reviewResult;

    @ApiModelProperty(value = "审签人")
    @TableField(REVIEW_USER)
    private String reviewUser;

    @ApiModelProperty(value = "是否生成结论书")
    @TableField(RESULT_FILE)
    private String resultFile;


}
