package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.LoginLogMapper;
import com.tree.clouds.coordination.model.entity.LoginLog;
import com.tree.clouds.coordination.model.vo.LoginLogPageVO;
import com.tree.clouds.coordination.service.LoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登入日志 服务实现类
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    public IPage<LoginLog> loginLogPage(LoginLogPageVO loginLogPageVO) {
        IPage<LoginLog> page = loginLogPageVO.getPage();
        return this.baseMapper.loginLogPage(page, loginLogPageVO);

    }

    @Override
    public void updateLongTime(String userAccount) {
        LoginLog loginLog = this.baseMapper.loginInfo(userAccount);
        if (loginLog != null) {
            loginLog.setLongTime(loginLog.getCreatedTime() + " - " + DateUtil.now());
        }
    }
}
