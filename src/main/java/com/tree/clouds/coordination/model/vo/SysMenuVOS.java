package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuVOS {
    @ApiModelProperty("菜单")
    private List<SysMenuVO> sysMenuVOS;
}
