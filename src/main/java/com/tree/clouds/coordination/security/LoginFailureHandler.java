package com.tree.clouds.coordination.security;

import cn.hutool.json.JSONUtil;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.model.entity.LoginLog;
import com.tree.clouds.coordination.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private LoginLogService loginLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //登入日志
        String ipAddresses = request.getRemoteAddr();
        String username = request.getParameter("username");
        LoginLog loginLog = new LoginLog();
        loginLog.setErrorInfo(exception.getMessage());
        loginLog.setErrorSort("账号或密码错误");
        loginLog.setIp(ipAddresses);
        loginLog.setAccount(username);
        loginLogService.save(loginLog);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        RestResponse result = RestResponse.fail(exception.getMessage());
        if ("Bad credentials".equals(exception.getMessage())) {
            result.setMsg("密码错误,请重新输入!");
        }
        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
