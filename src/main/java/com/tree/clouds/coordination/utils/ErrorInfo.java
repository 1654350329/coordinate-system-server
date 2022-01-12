package com.tree.clouds.coordination.utils;

import lombok.Data;

@Data
public class ErrorInfo {
    private int code;
    private String message;

    public ErrorInfo() {
    }

    public ErrorInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
