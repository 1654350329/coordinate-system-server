package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.AppraiseBO;
import com.tree.clouds.coordination.model.vo.AppraiseInfoVO;
import com.tree.clouds.coordination.model.vo.AppraisePageVO;
import com.tree.clouds.coordination.model.vo.AppraiseVO;
import com.tree.clouds.coordination.model.vo.PublicIdReqVO;
import com.tree.clouds.coordination.service.AppraiseService;
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
    public RestResponse<IPage<AppraiseBO>> appraisePage(@RequestBody AppraisePageVO appraisePageVO) {
        IPage<AppraiseBO> page = appraiseService.appraisePage(appraisePageVO);
        return RestResponse.ok(page);
    }

    @Log("添加鉴定")
    @PostMapping("/addAppraise")
    @ApiOperation(value = "添加鉴定")
    @PreAuthorize("hasAuthority('appraise:appraise:add')")
    public RestResponse<Boolean> addAppraise(@Validated @RequestBody AppraiseVO appraiseVO) {
        appraiseService.addAppraise(appraiseVO);
        return RestResponse.ok(true);
    }

    @Log("鉴定意见")
    @PostMapping("/AppraiseInfoVO")
    @ApiOperation(value = "鉴定意见 id为 reportId")
//    @PreAuthorize("hasAuthority('appraise:appraise:add')")
    public RestResponse<AppraiseInfoVO> AppraiseInfoVO(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        AppraiseInfoVO appraiseInfoVO = appraiseService.appraiseInfoVO(publicIdReqVO.getId());
        return RestResponse.ok(appraiseInfoVO);
    }


}

