package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class CompletionMethodVOS {
    @ApiModelProperty("结论送达")
    @Valid
    private List<CompletionMethod> completionMethods;
}
