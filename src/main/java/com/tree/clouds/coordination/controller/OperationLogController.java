package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.entity.OperationLog;
import com.tree.clouds.coordination.model.vo.OperationLogPageVO;
import com.tree.clouds.coordination.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作日志 前端控制器
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@RestController
@RequestMapping("/operation-log")
@Api(value = "operation-log", tags = "操作日志模块")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    @PostMapping("/operationLogPage")
    @ApiOperation(value = "操作日志分页查询")
    @Log("操作日志分页查询")
    public RestResponse<IPage<OperationLog>> operationLogPage(@RequestBody OperationLogPageVO operationLogPageVO) {
        IPage<OperationLog> page = operationLogService.operationLogPage(operationLogPageVO);
        return RestResponse.ok(page);
    }
}

