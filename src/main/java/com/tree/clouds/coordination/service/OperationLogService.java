package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.entity.OperationLog;
import com.tree.clouds.coordination.model.vo.OperationLogPageVO;

import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
public interface OperationLogService extends IService<OperationLog> {

    IPage<OperationLog> operationLogPage(OperationLogPageVO operationLogPageVO);

    void exportOperationLog(OperationLogPageVO operationLogPageVO, HttpServletResponse response);
}
