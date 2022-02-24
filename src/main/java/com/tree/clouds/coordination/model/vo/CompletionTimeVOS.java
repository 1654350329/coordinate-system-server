package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class CompletionTimeVOS {
    @ApiModelProperty("送达时间")
    @Valid
    private List<CompletionTimeVO> completionTimeVOS;
}
