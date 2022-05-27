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
 * 评估表
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("evaluation_sheet")
@ApiModel(value = "认定评估分页", description = "评估表")
public class EvaluationSheet extends BaseEntity {

    public static final String EVALUATION_ID = "EVALUATION_ID";
    public static final String WRITING_BATCH_ID = "WRITING_BATCH_ID";
    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String NUMBER = "NUMBER";
    public static final String SORT = "SORT";
    public static final String CYCLE_TIME = "CYCLE_TIME";
    public static final String EVALUATION_NUMBER = "EVALUATION_NUMBER";
    public static final String EVALUATION_STATUS = "EVALUATION_STATUS";
    public static final String EXPERT_NUMBER = "EXPERT_NUMBER";
    public static final String ALTERNATIVE_EXPERT_NUMBER = "ALTERNATIVE_EXPERT_NUMBER";
    public static final String DRAW_TIME = "DRAW_TIME";
    public static final String DRAW_STATUS = "DRAW_STATUS";
    public static final String COMPLETE_STATUS = "COMPLETE_STATUS";
    public static final String UPLOAD_STATUS = "UPLOAD_STATUS";
    public static final String RELEASE_STATUS = "release_status";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "评估主键")
    @TableId(value = EVALUATION_ID, type = IdType.UUID)
    private String evaluationId;

    @ApiModelProperty(value = "行文批次号")
    @TableField(WRITING_BATCH_ID)
    private String writingBatchId;

    @ApiModelProperty(value = "年份")
    @TableField(YEAR)
    private Integer year;

    @ApiModelProperty(value = "月份")
    @TableField(MONTH)
    private Integer month;

    @ApiModelProperty(value = "类别0工 1病")
    @TableField(SORT)
    private Integer sort;

    @ApiModelProperty(value = "批次")
    @TableField(NUMBER)
    private Integer number;

    @ApiModelProperty(value = "时间周期")
    @TableField(CYCLE_TIME)
    private String cycleTime;

    @ApiModelProperty(value = "鉴定人数")
    @TableField(EVALUATION_NUMBER)
    private int evaluationNumber;

    @ApiModelProperty(value = "鉴定状态")
    @TableField(EVALUATION_STATUS)
    private String evaluationStatus;

    @ApiModelProperty(value = "参评专家人数")
    @TableField(EXPERT_NUMBER)
    private int expertNumber;

    @ApiModelProperty(value = "候补专家人数")
    @TableField(ALTERNATIVE_EXPERT_NUMBER)
    private int alternativeExpertNumber;

    @ApiModelProperty(value = "抽签时间")
    @TableField(DRAW_TIME)
    private String drawTime;

    @ApiModelProperty(value = "抽签状态 0未抽 1第一轮 2第二轮")
    @TableField(DRAW_STATUS)
    private int drawStatus;

    @ApiModelProperty(value = "是否行文批号完成审核")
    @TableField(COMPLETE_STATUS)
    private int completeStatus;

    @ApiModelProperty(value = "发布状态 0未发布 1已发布")
    @TableField(RELEASE_STATUS)
    private int releaseStatus;

    @ApiModelProperty(value = "附件状态 0未上传 1已上传")
    @TableField(UPLOAD_STATUS)
    private int uploadStatus;

    @ApiModelProperty(value = "专家列表")
    @TableField("expert_users")
    private String expertUsers;

    @ApiModelProperty(value = "通知书路径")
    @TableField(exist = false)
    private String filePath;
}
