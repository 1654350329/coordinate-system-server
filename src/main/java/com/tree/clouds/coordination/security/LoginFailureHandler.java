package com.tree.clouds.coordination.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.model.entity.LoginLog;
import com.tree.clouds.coordination.service.LoginLogService;
import com.tree.clouds.coordination.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //登入日志
        String ipAddresses = request.getRemoteAddr();
        String username = request.getParameter("username");
        LoginLog loginLog = new LoginLog();
        String errorInfo = exception.getMessage().equals("Bad credentials") ? "用户名或密码错误，请重新输入" : exception.getMessage();
        loginLog.setErrorInfo(errorInfo);
        loginLog.setErrorSort("账号或密码错误");
        loginLog.setIp(ipAddresses);
        loginLog.setAccount(username);
        loginLog.setStatus(2);
        loginLogService.save(loginLog);
        //连续输入5次锁定账号
        Integer errorNumber = (Integer) redisUtil.hget(Constants.ERROR_LOGIN, username);
        if (errorNumber != null && errorNumber < 5) {
            redisUtil.hset(Constants.ERROR_LOGIN, username, errorNumber + 1, 1000 * 60 * 10);
        }
        if (errorNumber != null && errorNumber >= 5) {
            redisUtil.hset(Constants.LOCK_ACCOUNT, username, DateUtil.now(), 1000 * 60 * 10);
        }
        redisUtil.hset(Constants.ERROR_LOGIN, username, 1, 1000 * 60 * 10);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        RestResponse result = RestResponse.fail(errorInfo);
        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }
}
