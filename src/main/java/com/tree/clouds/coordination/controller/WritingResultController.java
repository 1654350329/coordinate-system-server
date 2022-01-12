package com.tree.clouds.coordination.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;
import com.tree.clouds.coordination.service.WritingResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 行文名单
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/write-result")
@Api(value = "write-result", tags = "劳动能力伤残等级结论书模块")
public class WritingResultController {
    @Autowired
    private WritingResultService writingResultService;

    @PostMapping("/writingResultPage")
    @ApiOperation(value = "劳动能力伤残等级结论书分页")
    @Log("劳动能力伤残等级结论书分页")
    public Result writingResultPage(@RequestBody WritingResultPageVO writingResultPageVO) {
        IPage<WritingResultBO> page = writingResultService.writingResultPage(writingResultPageVO);
        return Result.succ(page);
    }

    @PostMapping("/writingBuild/{reportId}")
    @ApiOperation(value = "生成结论书")
    @Log("生成结论书")
    public Result writingBuild(@PathVariable String reportId) {
        writingResultService.writingBuild(reportId);
        return Result.succ(true);
    }
}
