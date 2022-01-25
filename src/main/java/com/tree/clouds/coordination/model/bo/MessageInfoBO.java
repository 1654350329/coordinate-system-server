package com.tree.clouds.coordination.model.bo;

import com.tree.clouds.coordination.model.entity.DataReport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "结论送达分页", description = "结论送达分页")
public class MessageInfoBO extends DataReport {
    @ApiModelProperty(value = "主键")
    private String messageId;

    @ApiModelProperty(value = "行文批号")
    private String writingBatchId;

    @ApiModelProperty(value = "送达状态")
    private String messageStatus;

    @ApiModelProperty(value = "送达时间")
    private String completionTime;

    @ApiModelProperty(value = "发送方式(01窗口领取 02邮寄 03 公示)")
    private String completionMethod;

    @ApiModelProperty(value = "结论书文件id")
    private String fileId;
}
