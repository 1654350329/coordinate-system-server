package com.tree.clouds.coordination.common.exception;

import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.AccessDeniedException;

/**
 * 自定义异常处理类
 * 针对不同的异常自定义不同的方法
 * 环绕通知
 * 切面:针对所有的controller中抛出的异常
 * 若使用@ControllerAdvice,则不会自动转换为JSON格式
 */
@RestControllerAdvice
@Priority(1)
public class RestExceptionHandler {

    /**
     * 基础异常处理
     *
     * @param e
     * @return ErrorInfo
     */
    @ExceptionHandler({InvocationTargetException.class})
    public Result businessExceptionHandler(HttpServletRequest request, InvocationTargetException e) throws BaseBusinessException {
        return Result.fail(e.getMessage());
    }

    /**
     * 业务权限异常处理
     *
     * @param e
     * @return ErrorInfo
     */
    @ExceptionHandler({AccessDeniedException.class})
    public Result accessDeniedExceptionHandler(HttpServletRequest request, AccessDeniedException e) throws BaseBusinessException {
        return Result.fail(401, e.getMessage(), null);
    }

    // 实体校验异常捕获
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    /**
     * 只要抛出该类型异常,则由此方法处理
     * 并由此方法响应出异常状态码及消息
     * 如:RoleController.getRoleById(String id)方法
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Result handleException(HttpServletRequest request, Exception e) throws Exception {
        return Result.fail(500, e.getMessage(), null);
    }

    @ExceptionHandler(value = Throwable.class)
    public Result Throwable(HttpServletRequest request, Throwable e) throws Exception {
        return Result.fail(500, e.getMessage(), null);
    }


}