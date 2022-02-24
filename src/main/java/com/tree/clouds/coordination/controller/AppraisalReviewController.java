package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.AppraisalReviewBO;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import com.tree.clouds.coordination.model.vo.AppraisalReviewVO;
import com.tree.clouds.coordination.model.vo.AppraisalReviewVOS;
import com.tree.clouds.coordination.service.AppraisalReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 复核表 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/appraisal-review")
@Api(value = "appraisal-review", tags = "鉴定复核模块")
public class AppraisalReviewController {

    @Autowired
    private AppraisalReviewService appraisalReviewService;

    @PostMapping("/appraisalReviewPage")
    @ApiOperation(value = "鉴定复核分页查询")
    @Log("鉴定复核分页查询")
    @PreAuthorize("hasAuthority('appraisal:review:list')")
    public RestResponse<IPage<AppraisalReviewBO>> appraisePage(@RequestBody AppraisalReviewPageVO appraisePageVO) {
        IPage<AppraisalReviewBO> page = appraisalReviewService.appraisalReviewPage(appraisePageVO);
        return RestResponse.ok(page);
    }

    @Log("添加鉴定复核")
    @PostMapping("/addAppraisalReview")
    @ApiOperation(value = "添加鉴定复核")
    @PreAuthorize("hasAuthority('appraisal:review:add')")
    public RestResponse<Boolean> addAppraisalReview(@Validated @RequestBody AppraisalReviewVOS appraisalReviewVOS) {
        for (AppraisalReviewVO appraisalReviewVO : appraisalReviewVOS.getAppraisalReviewVOS()) {
            appraisalReviewService.addAppraisalReview(appraisalReviewVO);
        }
        return RestResponse.ok(true);
    }

}

