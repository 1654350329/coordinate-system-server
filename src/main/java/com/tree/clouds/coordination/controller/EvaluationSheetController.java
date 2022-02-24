package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.entity.EvaluationSheet;
import com.tree.clouds.coordination.model.vo.*;
import com.tree.clouds.coordination.service.EvaluationSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    @ApiOperation("创建待审表 上报id")
    @Log("创建待审表")
    @PreAuthorize("hasAuthority('evaluation:sheet:add')")
    public RestResponse<Boolean> addEvaluationSheet(@RequestBody PublicIdsReqVO publicIdsReqVO) {
        evaluationSheetService.addEvaluationSheet(publicIdsReqVO.getIds());
        return RestResponse.ok(true);
    }

    @PostMapping("/joinEvaluationSheet")
    @ApiOperation("加入待审表")
    @Log("加入待审表")
    @PreAuthorize("hasAuthority('evaluation:sheet:join')")
    public RestResponse<Boolean> joinEvaluationSheet(@RequestBody JoinEvaluationSheetVO joinEvaluationSheetVO) {
        evaluationSheetService.joinEvaluationSheet(joinEvaluationSheetVO.getWritingBatchId(), joinEvaluationSheetVO.getReportIds());
        return RestResponse.ok(true);
    }

    @PostMapping("/writingBatch")
    @ApiOperation("查询待审批次号")
    @Log("查询待审批次号")
    public RestResponse<List<String>> writingBatch() {
        List<String> writingBatchIds = evaluationSheetService.writingBatch();
        return RestResponse.ok(writingBatchIds);
    }

    @PostMapping("/evaluationSheetPage")
    @ApiOperation("评估分页")
    @Log("评估分页")
    @PreAuthorize("hasAuthority('evaluation:sheet:list')")
    public RestResponse<IPage<EvaluationSheet>> evaluationSheetPage(@RequestBody EvaluationSheetPageVO evaluationSheetPageVO) {
        IPage<EvaluationSheet> sheetIPage = evaluationSheetService.evaluationSheetPage(evaluationSheetPageVO);
        return RestResponse.ok(sheetIPage);
    }

    @PostMapping("/evaluationSheetDetail")
    @ApiOperation("评估详情")
    @Log("评估分页")
//    @PreAuthorize("hasAuthority('evaluation:sheet:list')")
    public RestResponse<EvaluationSheet> evaluationSheetDetail(@RequestBody PublicIdReqVO publicIdReqVO) {
        EvaluationSheet evaluationSheet = evaluationSheetService.evaluationSheetDetail(publicIdReqVO.getId());
        return RestResponse.ok(evaluationSheet);
    }

    @PostMapping("/draw")
    @ApiOperation("抽签")
    @Log("抽签")
    @PreAuthorize("hasAuthority('evaluation:sheet:draw')")
    public RestResponse<Boolean> draw(@Validated @RequestBody DrawVO drawVO) {
        evaluationSheetService.draw(drawVO);
        return RestResponse.ok(true);
    }

    @PostMapping("/drawInfo")
    @ApiOperation("抽签信息")
    @Log("抽签信息")
    public RestResponse<Map<String, IPage<ExpertVO>>> drawInfo(@Validated @RequestBody DrawInfoVO drawInfoVO) {
        Map<String, IPage<ExpertVO>> expertVOList = evaluationSheetService.drawInfo(drawInfoVO);
        return RestResponse.ok(expertVOList);
    }

    @PostMapping("/reportDetailPage")
    @ApiOperation("获取上报信息")
    @Log("获取上报信息")
    public RestResponse<IPage<DataReportBO>> reportDetailPage(@Validated @RequestBody WritingBatchVO writingBatchVO) {
        IPage<DataReportBO> page = evaluationSheetService.reportDetailPage(writingBatchVO);
        return RestResponse.ok(page);
    }

    @PostMapping("/expertDetailPage")
    @ApiOperation("获取专家组信息")
    @Log("获取专家组信息")
    public RestResponse<IPage<ExpertDetailVO>> expertDetailPage(@Validated @RequestBody WritingBatchVO writingBatchVO) {
        IPage<ExpertDetailVO> page = evaluationSheetService.expertDetailPage(writingBatchVO);
        return RestResponse.ok(page);
    }

//    @PostMapping("/drawRebuild")
//    @ApiOperation("重置抽签")
//    @Log("重置抽签")
//    @PreAuthorize("hasAuthority('evaluation:sheet:drawRebuild')")
//    public RestResponse<Boolean> drawRebuild(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
//        Boolean aBoolean = evaluationSheetService.drawRebuild(publicIdReqVO.getId());
//        return RestResponse.ok(aBoolean);
//    }

    @PostMapping("/drawGroupLeader")
    @ApiOperation("设置参评专家组长")
    @Log("设置参评专家组长")
    @PreAuthorize("hasAuthority('evaluation:sheet:drawGroupLeader')")
    public RestResponse<Boolean> drawGroupLeader(@Validated @RequestBody EvaluationSheetDetailVO evaluationSheetDetailVO) {
        Boolean aBoolean = evaluationSheetService.drawGroupLeader(evaluationSheetDetailVO);
        return RestResponse.ok(aBoolean);
    }

    @PostMapping("/removeExpert")
    @ApiOperation("移除专家")
    @Log("移除专家")
    @PreAuthorize("hasAuthority('evaluation:sheet:remove')")
    public RestResponse<Boolean> removeExpert(@Validated @RequestBody EvaluationSheetDetailVO evaluationSheetDetailVO) {
        Boolean aBoolean = evaluationSheetService.removeExpert(evaluationSheetDetailVO);
        return RestResponse.ok(aBoolean);
    }

    @PostMapping("/addEvaluation")
    @ApiOperation("添加参评")
    @Log("添加参评")
    @PreAuthorize("hasAuthority('evaluation:sheet:addEvaluation')")
    public RestResponse<Boolean> addEvaluation(@Validated @RequestBody EvaluationSheetDetailVO evaluationSheetDetailVO) {
        Boolean aBoolean = evaluationSheetService.addEvaluation(evaluationSheetDetailVO);
        return RestResponse.ok(aBoolean);
    }

    @PostMapping("/releaseEvaluation")
    @ApiOperation("发布评估")
    @Log("发布评估")
    @PreAuthorize("hasAuthority('evaluation:sheet:release')")
    public RestResponse<Boolean> releaseEvaluation(@Validated @RequestBody EvaluationReleaseVOS evaluationReleaseVOS) {
        for (EvaluationReleaseVO evaluationReleaseVO : evaluationReleaseVOS.getEvaluationReleaseVOS()) {
            evaluationSheetService.releaseEvaluation(evaluationReleaseVO);
        }
        return RestResponse.ok(true);
    }


}

