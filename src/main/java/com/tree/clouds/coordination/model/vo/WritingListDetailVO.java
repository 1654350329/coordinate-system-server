package com.tree.clouds.coordination.model.vo;

import com.tree.clouds.coordination.model.bo.ReportDetailInfoBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WritingListDetailVO {
    @ApiModelProperty(value = "人员名单")
    private List<ReportDetailInfoBO> dataReports;

    @ApiModelProperty(value = "时间")
    private String ctime;
    @ApiModelProperty(value = "时间")
    private String time;
    @ApiModelProperty(value = "审批人")
    private String examineUser;

    @ApiModelProperty(value = "复核人")
    private String appraisalReviewUser;

    @ApiModelProperty(value = "上报人")
    private String dataReportUser;


}
