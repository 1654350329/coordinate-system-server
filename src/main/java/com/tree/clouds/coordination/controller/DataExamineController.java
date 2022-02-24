package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.DataExamineBo;
import com.tree.clouds.coordination.model.vo.DataExamineVOS;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import com.tree.clouds.coordination.service.DataExamineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 资料初审 前端控制器
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@RestController
@RequestMapping("/data-examine")
@Api(value = "examine", tags = "资料初审模块")
public class DataExamineController {
    @Autowired
    private DataExamineService dataExamineService;

    @Log("审核资料可批量审核")
    @PostMapping("/addDataExamine")
    @ApiOperation("审核资料可批量审核")
    @PreAuthorize("hasAuthority('data:examine:examine')")
    public RestResponse<Boolean> addDataExamine(@RequestBody DataExamineVOS dataExamineVOS) {
        dataExamineService.addDataExamine(dataExamineVOS.getDataExamineVOS());
        return RestResponse.ok(true);
    }

    @Log("资料初审分页查询")
    @PostMapping("/dataExaminePage")
    @ApiOperation(value = "资料初审分页查询")
    @PreAuthorize("hasAuthority('data:examine:list')")
    public RestResponse<IPage<DataExamineBo>> dataReportPage(@RequestBody DataReportPageVO dataReport) {
        IPage<DataExamineBo> page = dataExamineService.dataExaminePage(dataReport);
        return RestResponse.ok(page);
    }
}

