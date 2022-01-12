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
 * 操作日志
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("operation_log")
@ApiModel(value = "OperationLog对象", description = "操作日志")
public class OperationLog extends BaseEntity {

    public static final String OPERATION_ID = "operation_id";
    public static final String IP = "ip";
    public static final String OPERATION = "operation";
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "操作id")
    @TableId(value = "operation_id", type = IdType.UUID)
    private String operationId;

    @ApiModelProperty(value = "ip地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "操作")
    @TableField("operation")
    private String operation;


}
