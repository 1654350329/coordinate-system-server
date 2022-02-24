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
 * 文书编号;(一个文书编号有多个资料上报)
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("writing_batch")
@ApiModel(value = "WritingBatch对象", description = "文书编号;(一个文书编号有多个资料上报)")
public class WritingBatch extends BaseEntity {

    public static final String EVALUATION_REPORT_ID = "EVALUATION_REPORT_ID";
    public static final String WRITING_BATCH_ID = "WRITING_BATCH_ID";
    public static final String REPORT_ID = "report_ID";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = EVALUATION_REPORT_ID, type = IdType.UUID)
    private String evaluationReportId;

    @ApiModelProperty(value = "文书批号")
    @TableField(WRITING_BATCH_ID)
    private String writingBatchId;

    @ApiModelProperty(value = "资料上报主键")
    @TableField(REPORT_ID)
    private String reportId;

}
