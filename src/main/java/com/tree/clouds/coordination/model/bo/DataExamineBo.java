package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 林振坤
 * @description
 * @date 2022/1/3 0003 11:55
 */
@Data
@ApiModel(value = "资料初审分页", description = "资料审核分页")
public class DataExamineBo extends DataReport {
    @ApiModelProperty(value = "主键")
    private String dataExamineId;

    @ApiModelProperty(value = "审核状态")
    private int examineStatus;

    @ApiModelProperty(value = "审核描述")
    private String examineDescribe;

    @ApiModelProperty(value = "审核时间")
    private String examineTime;

    @ApiModelProperty(value = "审核人")
    private String examineUser;
}
