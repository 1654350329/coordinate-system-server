package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "资料上报分页", description = "资料上报分页")
public class DataReportBO extends DataReport {
    @ApiModelProperty(value = "劳动能力伤残等级结论书 null为不存在")
    private String reviewAndSignatureId;
}
