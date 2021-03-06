package com.tree.clouds.coordination.utils;

public class BaseBusinessException extends RuntimeException {


    private Integer code;

    private String message;

    public BaseBusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}