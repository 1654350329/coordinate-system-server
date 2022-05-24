package com.tree.clouds.coordination.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageInfoPage extends PageParam {

    @ApiModelProperty(value = "行文批次号")
    private String writingBatchId;

    @ApiModelProperty(value = "认定对象名称")
    private String identifiedName;

    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;


    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "送达状态")
    private String messageStatus;

    @ApiModelProperty(value = "发送方式(01窗口领取 02邮寄 03 公示)")
    private String completionMethod;

    @ApiModelProperty(value = "送达时间开始")
    private String completionTimeStart;

    @ApiModelProperty(value = "送达时间结束")
    private String completionTimeEnd;


}
