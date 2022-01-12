package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.entity.EvaluationSheet;
import com.tree.clouds.coordination.model.vo.*;
import com.tree.clouds.coordination.service.EvaluationSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评估表 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/evaluation-sheet")
@Api(value = "EvaluationSheet", tags = "认定评估模块")
public class EvaluationSheetController {
    @Autowired
    private EvaluationSheetService evaluationSheetService;

    @PostMapping("/addEvaluationSheet")
    @ApiOperation("创建待审表")
    @Log("创建待审表")
    @PreAuthorize("hasAuthority('evaluation:sheet:add')")
    public Result addEvaluationSheet(@RequestBody PublicIdsReqVO publicIdsReqVO) {
        evaluationSheetService.addEvaluationSheet(publicIdsReqVO.getIds());
        return Result.succ(true);
    }

    @PostMapping("/joinEvaluationSheet/{writingBatchId}")
    @ApiOperation("加入待审表")
    @Log("加入待审表")
    @PreAuthorize("hasAuthority('evaluation:sheet:join')")
    public Result joinEvaluationSheet(@RequestBody PublicIdsReqVO publicIdsReqVO, @PathVariable String writingBatchId) {
        evaluationSheetService.joinEvaluationSheet(writingBatchId, publicIdsReqVO.getIds());
        return Result.succ(true);
    }

    @PostMapping("/writingBatch")
    @ApiOperation("查询待审批次号")
    @Log("查询待审批次号")
    @PreAuthorize("hasAuthority('evaluation:sheet:writing')")
    public Result writingBatch() {
        List<String> writingBatchIds = evaluationSheetService.writingBatch();
        return Result.succ(writingBatchIds);
    }

    @PostMapping("/evaluationSheetPage")
    @ApiOperation("评估分页")
    @Log("评估分页")
    @PreAuthorize("hasAuthority('evaluation:sheet:list')")
    public Result evaluationSheetPage(@RequestBody EvaluationSheetPageVO evaluationSheetPageVO) {
        IPage<EvaluationSheet> sheetIPage = evaluationSheetService.evaluationSheetPage(evaluationSheetPageVO);
        return Result.succ(sheetIPage);
    }

    @PostMapping("/draw")
    @ApiOperation("抽签")
    @Log("抽签")
    @PreAuthorize("hasAuthority('evaluation:sheet:draw')")
    public Result draw(@Validated @RequestBody DrawVO drawVO) {
        List<ExpertVO> expertVOList = evaluationSheetService.draw(drawVO);
        return Result.succ(expertVOList);
    }

    @PostMapping("/drawInfo/{writingBatchId}")
    @ApiOperation("抽签信息")
    @Log("抽签信息")
    @PreAuthorize("hasAuthority('evaluation:sheet:drawInfo')")
    public Result drawInfo(@PathVariable String writingBatchId) {
        List<ExpertVO> expertVOList = evaluationSheetService.drawInfo(writingBatchId);
        return Result.succ(expertVOList);
    }

    @PostMapping("/drawRebuild/{writingBatchId}")
    @ApiOperation("重置抽签")
    @Log("重置抽签")
    @PreAuthorize("hasAuthority('evaluation:sheet:drawRebuild')")
    public Result drawRebuild(@PathVariable String writingBatchId) {
        Boolean aBoolean = evaluationSheetService.drawRebuild(writingBatchId);
        return Result.succ(aBoolean);
    }

    @PostMapping("/drawGroupLeader")
    @ApiOperation("设置参评专家组长")
    @Log("设置参评专家组长")
    @PreAuthorize("hasAuthority('evaluation:sheet:drawGroupLeader')")
    public Result drawGroupLeader(@Validated @RequestBody EvaluationSheetDetailVO evaluationSheetDetailVO) {
        Boolean aBoolean = evaluationSheetService.drawGroupLeader(evaluationSheetDetailVO);
        return Result.succ(aBoolean);
    }

    @PostMapping("/removeExpert")
    @ApiOperation("移除专家")
    @Log("移除专家")
    @PreAuthorize("hasAuthority('evaluation:sheet:remove')")
    public Result removeExpert(@Validated @RequestBody EvaluationSheetDetailVO evaluationSheetDetailVO) {
        Boolean aBoolean = evaluationSheetService.removeExpert(evaluationSheetDetailVO);
        return Result.succ(aBoolean);
    }

    @PostMapping("/addEvaluation")
    @ApiOperation("添加参评")
    @Log("添加参评")
    @PreAuthorize("hasAuthority('evaluation:sheet:addEvaluation')")
    public Result addEvaluation(@Validated @RequestBody EvaluationSheetDetailVO evaluationSheetDetailVO) {
        Boolean aBoolean = evaluationSheetService.addEvaluation(evaluationSheetDetailVO);
        return Result.succ(aBoolean);
    }

    @PostMapping("/releaseEvaluation")
    @ApiOperation("发布评估")
    @Log("发布评估")
//    @PreAuthorize("hasAuthority('evaluation:sheet:release')")
    public Result releaseEvaluation(@Validated @RequestBody List<EvaluationReleaseVO> evaluationReleaseVOS) {
        for (EvaluationReleaseVO evaluationReleaseVO : evaluationReleaseVOS) {
            evaluationSheetService.releaseEvaluation(evaluationReleaseVO);
        }
        return Result.succ(true);
    }


}

