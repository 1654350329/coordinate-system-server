package com.tree.clouds.coordination.controller;

import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.Producer;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "Auth", tags = "用户登入模块")
public class AuthController {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码")
    public Result captcha() throws IOException {

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
        return Result.succ(map);
    }

    // 普通用户、超级管理员
//    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/test/pass")
    @ApiOperation(value = "密码加密")
    public Result pass() {

        // 加密后密码
        String password = bCryptPasswordEncoder.encode("111111");

        boolean matches = bCryptPasswordEncoder.matches("111111", password);

        System.out.println("匹配结果：" + matches);

        return Result.succ(password);
    }

}
