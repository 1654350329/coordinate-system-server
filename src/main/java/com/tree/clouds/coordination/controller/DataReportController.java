package com.tree.clouds.coordination.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import com.tree.clouds.coordination.model.vo.PublicIdReqVO;
import com.tree.clouds.coordination.model.vo.PublicIdsReqVO;
import com.tree.clouds.coordination.model.vo.UpdateDataReportVO;
import com.tree.clouds.coordination.service.DataReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public RestResponse<IPage<DataReportBO>> dataReportPage(@RequestBody DataReportPageVO dataReport) {
        IPage<DataReportBO> page = dataReportService.dataReportPage(dataReport);
        return RestResponse.ok(page);
    }

    @PostMapping("/dataList")
    @ApiOperation(value = "资料上报数据字典")
    @Log("资料上报数据字典")
//    @PreAuthorize("hasAuthority('data:report:list')")
    public RestResponse<Map> dataList() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("category", Arrays.asList("骨科", "烧伤科", "眼科", "呼吸科", "消化科"));
        map.put("type", Arrays.asList("委托任务", "指标任务"));
        map.put("titleGrade", Arrays.asList("正高级", "副高级", "中级", "助理级", "员级"));
        Map<Integer, String> sortMap = new HashMap();
        sortMap.put(0, "工");
        sortMap.put(1, "病");
        map.put("sort", sortMap);
        Map<Integer, String> examineProgressMap = new HashMap();
        examineProgressMap.put(0, "初始");
        examineProgressMap.put(1, "上报");
        examineProgressMap.put(2, "初审");
        examineProgressMap.put(3, "鉴定");
        examineProgressMap.put(4, "复核一");
        examineProgressMap.put(5, "复核二");
        examineProgressMap.put(6, "认定审签");
        map.put("examineProgressMap", examineProgressMap);
        Map<Integer, String> statusMap = new HashMap();
        statusMap.put(0, "正常");
        statusMap.put(1, "驳回");
        map.put("status", statusMap);
        Map<Integer, String> categoryMap = new HashMap();
        categoryMap.put(0, "正常");
        categoryMap.put(1, "驳回");
        map.put("status", statusMap);
        return RestResponse.ok(map);
    }

    @Log("资料上报详细查询")
    @PostMapping("/getDataReportDetail")
    @ApiOperation(value = "资料上报详细查询")
    public RestResponse<UpdateDataReportVO> getDataReportDetail(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        UpdateDataReportVO dataReportDetail = dataReportService.getDataReportDetail(publicIdReqVO.getId());
        return RestResponse.ok(dataReportDetail);
    }

    @PostMapping("/report")
    @ApiOperation(value = "资料上报")
    @Log("资料上报")
    @PreAuthorize("hasAuthority('data:report:report')")
    public RestResponse<Boolean> report(@Validated @RequestBody PublicIdsReqVO publicIdsReqVO) {
        dataReportService.report(publicIdsReqVO.getIds());
        return RestResponse.ok(true);
    }

    @PostMapping("/addDataReport")
    @ApiOperation(value = "新增资料")
    @Log("新增资料")
    @PreAuthorize("hasAuthority('data:report:save')")
    public RestResponse<Boolean> addDataReport(@Validated @RequestBody UpdateDataReportVO DataReportVO) {
        dataReportService.addDataReport(DataReportVO);
        return RestResponse.ok(true);
    }

    @PostMapping("/updateDataReport")
    @ApiOperation(value = "编辑资料")
    @Log("编辑资料")
    @PreAuthorize("hasAuthority('data:report:update')")
    public RestResponse<Boolean> updateDataReport(@Validated @RequestBody UpdateDataReportVO DataReportVO) {
        dataReportService.updateDataReport(DataReportVO);
        return RestResponse.ok(true);
    }

    @PostMapping("/deleteDataReport")
    @ApiOperation(value = "删除资料")
    @Log("删除资料")
    @PreAuthorize("hasAuthority('data:report:delete')")
    public RestResponse<Boolean> deleteDataReport(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        dataReportService.deleteDataReport(publicIdReqVO.getId());
        return RestResponse.ok(true);
    }

    @PostMapping("/revokeReport")
    @ApiOperation(value = "撤销资料上报")
    @Log("撤销资料上报")
    @PreAuthorize("hasAuthority('data:report:revoke')")
    public RestResponse<Boolean> revokeReport(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        dataReportService.revokeReport(publicIdReqVO.getId());
        return RestResponse.ok(true);
    }

}

