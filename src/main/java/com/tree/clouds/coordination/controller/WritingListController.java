package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.vo.PublicIdReqVO;
import com.tree.clouds.coordination.model.vo.WritingListDetailVO;
import com.tree.clouds.coordination.model.vo.WritingListPageVO;
import com.tree.clouds.coordination.model.vo.WritingListVO;
import com.tree.clouds.coordination.service.WritingListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public RestResponse<IPage<WritingListBO>> writingListPage(@RequestBody WritingListPageVO writingListPageVO) {
        IPage<WritingListBO> page = writingListService.writingListPage(writingListPageVO);
        return RestResponse.ok(page);
    }

    @PostMapping("/writingListUpload")
    @ApiOperation(value = "上传附件")
    @Log("上传附件")
    public RestResponse<Boolean> writingListUpload(@RequestBody WritingListVO writingListVO) {
        writingListService.writingListUpload(writingListVO);
        return RestResponse.ok(true);
    }

    @PostMapping("/writingListDetail")
    @ApiOperation(value = "获取详细信息 传参为行文批号:writingBatchId")
    @Log("获取详细信息")
    public RestResponse<WritingListDetailVO> writingListDetail(@RequestBody PublicIdReqVO publicIdReqVO) {
        WritingListDetailVO writingListVO = writingListService.writingListDetail(publicIdReqVO.getId());
        return RestResponse.ok(writingListVO);
    }

}

