package com.tree.clouds.coordination.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import com.tree.clouds.coordination.model.vo.PublicIdsReqVO;
import com.tree.clouds.coordination.model.vo.UpdateDataReportVO;
import com.tree.clouds.coordination.service.DataReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 资料上报 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/data-report")
@Api(value = "DataReport", tags = "资料上报模块")
public class DataReportController {
    @Autowired
    private DataReportService dataReportService;

    @PostMapping("/dataReportPage")
    @ApiOperation(value = "资料上报分页查询")
    @Log("资料上报分页查询")
    @PreAuthorize("hasAuthority('data:report:list')")
    public Result dataReportPage(@RequestBody DataReportPageVO dataReport) {
        IPage<DataReportBO> page = dataReportService.dataReportPage(dataReport);
        return Result.succ(page);
    }

    @Log("资料上报详细查询")
    @PostMapping("/getDataReportDetail/{reportId}")
    @ApiOperation(value = "资料上报详细查询")
    @PreAuthorize("hasAuthority('data:report:select')")
    public Result getDataReportDetail(@PathVariable String reportId) {
        UpdateDataReportVO dataReportDetail = dataReportService.getDataReportDetail(reportId);
        return Result.succ(dataReportDetail);
    }

    @PostMapping("/report")
    @ApiOperation(value = "资料上报")
    @Log("资料上报")
    @PreAuthorize("hasAuthority('data:report:report')")
    public Result report(@RequestBody PublicIdsReqVO publicIdsReqVO) {
        dataReportService.report(publicIdsReqVO.getIds());
        return Result.succ(true);
    }

    @PostMapping("/addDataReport")
    @ApiOperation(value = "新增资料")
    @Log("新增资料")
    @PreAuthorize("hasAuthority('data:report:save')")
    public Result addDataReport(@RequestBody UpdateDataReportVO DataReportVO) {
        dataReportService.addDataReport(DataReportVO);
        return Result.succ(true);
    }

    @PostMapping("/updateDataReport")
    @ApiOperation(value = "编辑资料")
    @Log("编辑资料")
    @PreAuthorize("hasAuthority('data:report:update')")
    public Result updateDataReport(UpdateDataReportVO DataReportVO) {
        dataReportService.updateDataReport(DataReportVO);
        return Result.succ(true);
    }

    @PostMapping("/deleteDataReport/{id}")
    @ApiOperation(value = "删除资料")
    @Log("删除资料")
    @PreAuthorize("hasAuthority('data:report:delete')")
    public Result deleteDataReport(@PathVariable String id) {
        dataReportService.deleteDataReport(id);
        return Result.succ(true);
    }

    @PostMapping("/revokeReport/{id}")
    @ApiOperation(value = "撤销资料上报")
    @Log("撤销资料上报")
    @PreAuthorize("hasAuthority('data:report:revoke')")
    public Result revokeReport(@PathVariable String id) {
        dataReportService.revokeReport(id);
        return Result.succ(true);
    }

}

