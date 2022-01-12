package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.ReviewSignatureBO;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import com.tree.clouds.coordination.model.vo.ReviewSignatureVO;
import com.tree.clouds.coordination.service.ReviewSignatureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 认定审签 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/review-signature")
@Api(value = "review-signature", tags = "认定审签模块")
public class ReviewSignatureController {
    @Autowired
    private ReviewSignatureService reviewSignatureService;

    @PostMapping("/reviewSignaturePage")
    @ApiOperation(value = "认定审签分页查询")
    @Log("认定审签分页查询")
    public Result reviewSignaturePage(@RequestBody AppraisalReviewPageVO appraisePageVO) {
        IPage<ReviewSignatureBO> page = reviewSignatureService.reviewSignaturePage(appraisePageVO);
        return Result.succ(page);
    }

    @PostMapping("/addReviewSignature")
    @ApiOperation(value = "添加审签")
    @Log("添加审签")
    public Result addReviewSignature(@RequestBody ReviewSignatureVO reviewSignatureVO) {
        reviewSignatureService.addReviewSignature(reviewSignatureVO);
        return Result.succ(true);
    }


}

