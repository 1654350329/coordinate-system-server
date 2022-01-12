package com.tree.clouds.coordination.security;

import com.tree.clouds.coordination.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * spring security 异常处理
 */
@Slf4j
@RestController
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH)
    Result error(HttpServletRequest request, HttpServletResponse response) {
        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring.
        // Here we just define response body.
        Map<String, Object> errorMap = getErrorAttributes(request);
        //定义返回格式
        Result d = new Result();
        d.setMsg(Objects.requireNonNull(errorMap.get("message")).toString());
        d.setCode(Integer.parseInt(Objects.requireNonNull(errorMap.get("status")).toString()));
        response.setStatus(HttpServletResponse.SC_OK);
        return d;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(requestAttributes, false);
    }

}