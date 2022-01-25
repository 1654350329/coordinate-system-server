package com.tree.clouds.coordination.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.vo.PublicIdReqVO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;
import com.tree.clouds.coordination.service.WritingResultService;
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
@RequestMapping("/write-result")
@Api(value = "write-result", tags = "劳动能力伤残等级结论书模块")
public class WritingResultController {
    @Autowired
    private WritingResultService writingResultService;

    @PostMapping("/writingResultPage")
    @ApiOperation(value = "劳动能力伤残等级结论书分页")
    @Log("劳动能力伤残等级结论书分页")
    public RestResponse<IPage<WritingResultBO>> writingResultPage(@RequestBody WritingResultPageVO writingResultPageVO) {
        IPage<WritingResultBO> page = writingResultService.writingResultPage(writingResultPageVO);
        return RestResponse.ok(page);
    }

    @PostMapping("/writingBuild")
    @ApiOperation(value = "生成结论书 传参为 上报主键:reportId")
    @Log("生成结论书")
    public RestResponse<Boolean> writingBuild(@RequestBody PublicIdReqVO publicIdReqVO) {
        writingResultService.writingBuild(publicIdReqVO.getId());
        return RestResponse.ok(true);
    }
}
