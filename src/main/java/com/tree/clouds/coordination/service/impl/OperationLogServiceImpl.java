package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.mapper.OperationLogMapper;
import com.tree.clouds.coordination.model.entity.OperationLog;
import com.tree.clouds.coordination.model.entity.RoleManage;
import com.tree.clouds.coordination.model.vo.OperationLogPageVO;
import com.tree.clouds.coordination.service.OperationLogService;
import com.tree.clouds.coordination.service.RoleManageService;
import com.tree.clouds.coordination.utils.DownloadFile;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Autowired
    private RoleManageService roleManageService;

    @Override
    public IPage<OperationLog> operationLogPage(OperationLogPageVO operationLogPageVO) {
        List<RoleManage> manages = roleManageService.getByUserId(LoginUserUtil.getUserId());
        List<String> collect = manages.stream().map(RoleManage::getRoleCode).collect(Collectors.toList());
        if (!collect.contains("ROLE_admin")) {
            operationLogPageVO.setUserName(LoginUserUtil.getUserName());
        }
        IPage<OperationLog> page = operationLogPageVO.getPage();
        return this.baseMapper.operationLogPage(page, operationLogPageVO);
    }

    @Override
    public void exportOperationLog(OperationLogPageVO operationLogPageVO, HttpServletResponse response) {
        List<OperationLog> records = operationLogPage(operationLogPageVO).getRecords();
        String fileName = "操作日志" + DateUtil.formatDate(new Date()) + ".xlsx";
        EasyExcel.write(Constants.TMP_HOME + fileName, OperationLog.class).sheet("操作日志")
                .doWrite(records);
        byte[] bytes = DownloadFile.File2byte(new File(Constants.TMP_HOME + fileName));
        DownloadFile.downloadFile(bytes, fileName, response, false);
    }
}
