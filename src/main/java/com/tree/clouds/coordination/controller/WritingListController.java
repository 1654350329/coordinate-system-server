package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.vo.WritingListDetailVO;
import com.tree.clouds.coordination.model.vo.WritingListPageVO;
import com.tree.clouds.coordination.model.vo.WritingListVO;
import com.tree.clouds.coordination.service.WritingListService;
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
@RequestMapping("/write-list")
@Api(value = "appraisal-review", tags = "行文名单模块")
public class WritingListController {

    @Autowired
    private WritingListService writingListService;

    @PostMapping("/writingListPage")
    @ApiOperation(value = "行文名单分页")
    @Log("行文名单分页")
    public Result writingListPage(@RequestBody WritingListPageVO writingListPageVO) {
        IPage<WritingListBO> page = writingListService.writingListPage(writingListPageVO);
        return Result.succ(page);
    }

    @PostMapping("/writingListUpload")
    @ApiOperation(value = "上传附件")
    @Log("上传附件")
    public Result writingListUpload(@RequestBody WritingListVO writingListVO) {
        writingListService.writingListUpload(writingListVO);
        return Result.succ(true);
    }

    @PostMapping("/writingListDetail/{writingBatchId}")
    @ApiOperation(value = "获取详细信息")
    @Log("获取详细信息")
    public Result writingListDetail(@PathVariable String writingBatchId) {
        WritingListDetailVO writingListVO = writingListService.writingListDetail(writingBatchId);
        return Result.succ(writingListVO);
    }

}

