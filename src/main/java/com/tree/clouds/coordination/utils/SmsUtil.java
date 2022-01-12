package com.tree.clouds.coordination.utils;

import cn.hutool.http.HttpUtil;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SmsUtil {
    private static String account = "123162";
    private static String password = "naKfTX";
    private static String extno = "10690371";
    private static String url = "http://47.99.242.143:7862/sms";


    public static void endMs(String content, List<String> phones) {
        String phone = String.join(",", phones);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("action", "send");
        paramMap.put("account", account);
        paramMap.put("password", password);
        paramMap.put("mobile", phone);
        paramMap.put("content", new String(content.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        paramMap.put("extno", extno);
        paramMap.put("rt", "json");
        String result = HttpUtil.post(url, paramMap);
        System.out.println("result = " + result);
    }

    public static void main(String[] args) {

        endMs("【测试】测试", Collections.singletonList("15280165562"));
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("action", "balance");
//        paramMap.put("account", account);
//        paramMap.put("rt", "json");
//        String result= HttpUtil.post("http://47.99.242.143:7862/sms", paramMap);
//        System.out.println("result = " + result);
    }
}
