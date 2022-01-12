package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.entity.LoginLog;
import com.tree.clouds.coordination.model.vo.LoginLogPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 登入日志 Mapper 接口
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    IPage<LoginLog> loginLogPage(IPage<LoginLog> page, @Param("loginLogPageVO") LoginLogPageVO loginLogPageVO);

    LoginLog loginInfo(@Param("account") String account);
}
