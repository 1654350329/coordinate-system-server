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
 * 评估详细表
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("evaluation_sheet_detail")
@ApiModel(value = "EvaluationSheetDetail对象", description = "评估详细表")
public class EvaluationSheetDetail extends BaseEntity implements Serializable {

    public static final String EVALUATION_DETAIL_ID = "EVALUATION_DETAIL_ID";
    public static final String EVALUATION_ID = "EVALUATION_ID";
    public static final String USER_ID = "USER_ID";
    public static final String EXPERT_TYPE = "EXPERT_TYPE";
    public static final String GROUP_LEADER = "GROUP_LEADER";
    public static final String PARTICIPATION_STATUS = "PARTICIPATION_STATUS";
    public static final String REMARK = "REMARK";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "评估详细id")
    @TableId(value = EVALUATION_DETAIL_ID, type = IdType.UUID)
    private String evaluationDetailId;

    @ApiModelProperty(value = "评估主键")
    @TableField(EVALUATION_ID)
    private String evaluationId;

    @ApiModelProperty(value = "专家id")
    @TableField(USER_ID)
    private String userId;

    @ApiModelProperty(value = "专家类型(1 参评专家 2备选专家)")
    @TableField(EXPERT_TYPE)
    private int expertType;

    @ApiModelProperty(value = "是否组长")
    @TableField(GROUP_LEADER)
    private int groupLeader;

    @ApiModelProperty(value = "参与状态 0不参与 1参与")
    @TableField(PARTICIPATION_STATUS)
    private int participationStatus;

    @ApiModelProperty(value = "移除原因")
    @TableField(REMARK)
    private String remark;


}
