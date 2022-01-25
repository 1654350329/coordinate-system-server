package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.MessageInfoBO;
import com.tree.clouds.coordination.model.vo.CompletionMethod;
import com.tree.clouds.coordination.model.vo.CompletionTimeVO;
import com.tree.clouds.coordination.model.vo.MessageInfoPage;
import com.tree.clouds.coordination.service.MessageInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 结论送达情况 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/message-info")
@Api(value = "message-info", tags = "结论送达模块")
public class MessageInfoController {

    @Autowired
    private MessageInfoService messageInfoService;

    @PostMapping("/messageInfoPage")
    @ApiOperation(value = "结论送达分页")
    @Log("结论送达分页")
    public RestResponse<IPage<MessageInfoBO>> messageInfoPage(@RequestBody MessageInfoPage messageInfoPage) {
        IPage<MessageInfoBO> page = messageInfoService.messageInfoPage(messageInfoPage);
        return RestResponse.ok(page);
    }

    @PostMapping("/completionMethod")
    @ApiOperation(value = "送达方式")
    @Log("送达方式")
    public RestResponse<Boolean> completionMethod(@Validated @RequestBody CompletionMethod completionMethod) {
        messageInfoService.completionMethod(completionMethod.getMessageId(), completionMethod.getType());
        return RestResponse.ok(true);
    }

    @PostMapping("/completionTime")
    @ApiOperation(value = "送达时间")
    @Log("送达时间")
    public RestResponse<Boolean> completionTime(@Validated @RequestBody CompletionTimeVO completionTimeVO) {
        messageInfoService.completionTime(completionTimeVO.getMessageId(), completionTimeVO.getTime());
        return RestResponse.ok(true);
    }
}

