package com.tree.clouds.coordination.security;

import cn.hutool.json.JSONUtil;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.service.LoginLogService;
import com.tree.clouds.coordination.utils.JwtUtils;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private LoginLogService loginLogService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        loginLogService.updateLongTime(LoginUserUtil.getUserAccount());
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader(jwtUtils.getHeader(), "");

        RestResponse result = RestResponse.ok("");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
