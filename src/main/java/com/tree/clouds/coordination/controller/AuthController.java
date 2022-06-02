package com.tree.clouds.coordination.controller;

import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.Producer;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.service.UserManageService;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import com.tree.clouds.coordination.utils.QiniuUtil;
import com.tree.clouds.coordination.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Api(value = "auth", tags = "用户登入模块")
public class AuthController {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private QiniuUtil qiniuUtil;

    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码")
    public RestResponse<Map> captcha() throws IOException {

        String key = UUID.randomUUID().toString();
        String code = producer.createText();
        key = "a";
        code = "123456";
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        redisUtil.hset("captcha", key, code, 120);
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("captchaImg", base64Img);
        return RestResponse.ok(map);
    }


    /**
     * 文件上传
     *
     * @param
     * @return
     */
    @PostMapping("/getQNToken")
    @ApiOperation(value = "获取七牛token")
    public RestResponse<String> getQNToken() {
        return RestResponse.ok(qiniuUtil.getUploadCredential());
    }

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息")
    private RestResponse<Map> getInfo() {
        //roles, name, avatar, introduction
        UserManage user = userManageService.getById(LoginUserUtil.getUserId());
        String userAuthorityInfo = userManageService.getUserAuthorityInfo(user.getUserId());
        List<String> roles = Arrays.stream(userAuthorityInfo.split(",")).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("roles", roles);
        map.put("name", user.getUserName());

        return RestResponse.ok(map);
    }

    // 普通用户、超级管理员
//    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/test/pass")
    @ApiOperation(value = "密码加密")
    public RestResponse<String> pass() {

        // 加密后密码
        String password = bCryptPasswordEncoder.encode("111111");

        boolean matches = bCryptPasswordEncoder.matches("111111", password);

        System.out.println("匹配结果：" + matches);

        return RestResponse.ok(password);
    }

}
