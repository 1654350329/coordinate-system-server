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
 * 字典
 * </p>
 *
 * @author LZK
 * @since 2022-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dictionary")
@ApiModel(value = "Dictionary对象", description = "字典")
public class Dictionary extends BaseEntity {
    public static final String DICTIONARY_ID = "dictionary_id";
    public static final String NAME = "name";
    public static final String CODE = "code";
    public static final String DICTIONARY_TYPE = "dictionary_type";

    @ApiModelProperty(value = "主键")
    @TableId(value = "dictionary_id", type = IdType.UUID)
    private String dictionaryId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "类型")
    @TableField("dictionary_type")
    private String dictionaryType;


}
