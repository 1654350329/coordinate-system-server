package com.tree.clouds.coordination.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.model.entity.LoginLog;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.service.LoginLogService;
import com.tree.clouds.coordination.service.UserManageService;
import com.tree.clouds.coordination.utils.JwtUtils;
import com.tree.clouds.coordination.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserManageService userManageService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //登入日志
        String ipAddresses = request.getRemoteAddr();
        String username = request.getParameter("username");
        LoginLog loginLog = new LoginLog();
        loginLog.setIp(ipAddresses);
        loginLog.setAccount(username);
        loginLog.setStatus(1);
        loginLogService.save(loginLog);
        //清除登入失败记录
        redisUtil.hdel(Constants.ERROR_LOGIN, username);
        UserManage userByAccount = userManageService.getUserByAccount(username);
        boolean passwordStatus = false;
        if (DateUtil.parseDate(userByAccount.getPasswordTime()).getTime() + 1000 * 60 * 60 * 24 * 85 > new Date().getTime()) {
            passwordStatus = true;
        }

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        // 生成jwt，并放置到请求头中
        String jwt = jwtUtils.generateToken(authentication.getName());
        response.setHeader(jwtUtils.getHeader(), jwt);
        Map<String, Object> map = new HashMap<>();
        map.put(jwtUtils.getHeader(), jwt);
        map.put("passwordStatus", passwordStatus);
        RestResponse result = RestResponse.ok(map);

        outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }

}
