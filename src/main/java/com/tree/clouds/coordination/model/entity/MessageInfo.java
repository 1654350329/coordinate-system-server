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
 * 结论送达情况
 * </p>
 *
 * @author LZK
 * @since 2021-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("message_info")
@ApiModel(value = "MessageInfo对象", description = "结论送达情况")
public class MessageInfo extends BaseEntity implements Serializable {

    public static final String MESSAGE_ID = "MESSAGE_ID";
    public static final String WRITING_BATCH_ID = "WRITING_BATCH_ID";
    public static final String MESSAGE_STATUS = "MESSAGE_STATUS";
    public static final String COMPLETION_TIME = "COMPLETION_TIME";
    public static final String COMPLETION_METHOD = "COMPLETION_METHOD";
    public static final String REPORT_ID = "report_Id";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @TableId(value = MESSAGE_ID, type = IdType.UUID)
    private String messageId;

    @ApiModelProperty(value = "行文批号")
    @TableField(WRITING_BATCH_ID)
    private String writingBatchId;

    @ApiModelProperty(value = "资料上报主键")
    @TableField("report_id")
    private String reportId;

    @ApiModelProperty(value = "送达状态(0 未送达 1已送达)")
    @TableField(MESSAGE_STATUS)
    private String messageStatus;

    @ApiModelProperty(value = "送达时间")
    @TableField(COMPLETION_TIME)
    private String completionTime;

    @ApiModelProperty(value = "发送方式(01窗口领取 02邮寄 03 公示)")
    @TableField(COMPLETION_METHOD)
    private String completionMethod;


}
