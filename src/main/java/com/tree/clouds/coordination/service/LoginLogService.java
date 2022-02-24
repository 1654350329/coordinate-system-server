package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.entity.LoginLog;
import com.tree.clouds.coordination.model.vo.ExportLoginLogVO;
import com.tree.clouds.coordination.model.vo.LoginLogPageVO;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登入日志 服务类
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
public interface LoginLogService extends IService<LoginLog> {

    IPage<LoginLog> loginLogPage(LoginLogPageVO loginLogPageVO);

    void updateLongTime(String userAccount);

    void exportLoginLog(ExportLoginLogVO exportLoginLogVO, HttpServletResponse response);

}
