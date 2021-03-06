package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "PublicIdsReqVO", description = "通用批量请求VO")
public class PublicIdsReqVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键", required = true)
    @NotEmpty(message = "主键不能为空")
    private List<String> ids;
}