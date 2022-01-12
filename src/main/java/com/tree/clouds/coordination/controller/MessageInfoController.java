package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.MessageInfoBO;
import com.tree.clouds.coordination.model.vo.MessageInfoPage;
import com.tree.clouds.coordination.service.MessageInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result messageInfoPage(@RequestBody MessageInfoPage messageInfoPage) {
        IPage<MessageInfoBO> page = messageInfoService.messageInfoPage(messageInfoPage);
        return Result.succ(page);
    }

    @PostMapping("/completionMethod/{messageId}/{type}")
    @ApiOperation(value = "送达方式")
    @Log("送达方式")
    public Result completionMethod(@PathVariable String messageId, @PathVariable String type) {
        messageInfoService.completionMethod(messageId, type);
        return Result.succ(true);
    }

    @PostMapping("/completionTime/{messageId}/{time}")
    @ApiOperation(value = "送达时间")
    @Log("送达时间")
    public Result completionTime(@PathVariable String messageId, @PathVariable String time) {
        messageInfoService.completionTime(messageId, time);
        return Result.succ(true);
    }
}

