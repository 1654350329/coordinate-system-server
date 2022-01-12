package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.AppraiseBO;
import com.tree.clouds.coordination.model.vo.AppraiseInfoVO;
import com.tree.clouds.coordination.model.vo.AppraisePageVO;
import com.tree.clouds.coordination.model.vo.AppraiseVO;
import com.tree.clouds.coordination.model.vo.ExpertGroupInfo;
import com.tree.clouds.coordination.service.AppraiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 鉴定表 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/appraise")
@Api(value = "appraise", tags = "鉴定模块")
public class AppraiseController {

    @Autowired
    private AppraiseService appraiseService;

    @Log("鉴定分页查询")
    @PostMapping("/appraisePage")
    @ApiOperation(value = "鉴定分页查询")
    @PreAuthorize("hasAuthority('appraise:appraise:list')")
    public Result appraisePage(@RequestBody AppraisePageVO appraisePageVO) {
        IPage<AppraiseBO> page = appraiseService.appraisePage(appraisePageVO);
        return Result.succ(page);
    }

    @Log("添加鉴定")
    @PostMapping("/addAppraise")
    @ApiOperation(value = "添加鉴定")
    @PreAuthorize("hasAuthority('appraise:appraise:add')")
    public Result addAppraise(@Validated @RequestBody AppraiseVO appraiseVO) {
        appraiseService.addAppraise(appraiseVO);
        return Result.succ(true);
    }

    @Log("专家组信息")
    @PostMapping("/ExpertGroupInfo/{reportId}")
    @ApiOperation(value = "专家组信息")
//    @PreAuthorize("hasAuthority('appraise:appraise:add')")
    public Result ExpertGroupInfo(@PathVariable String reportId) {
        List<ExpertGroupInfo> userManages = appraiseService.expertGroupInfo(reportId);
        return Result.succ(userManages);
    }

    @Log("鉴定意见")
    @PostMapping("/AppraiseInfoVO/{reportId}")
    @ApiOperation(value = "鉴定意见")
//    @PreAuthorize("hasAuthority('appraise:appraise:add')")
    public Result AppraiseInfoVO(@PathVariable String reportId) {
        AppraiseInfoVO appraiseInfoVO = appraiseService.appraiseInfoVO(reportId);
        return Result.succ(appraiseInfoVO);
    }


}

