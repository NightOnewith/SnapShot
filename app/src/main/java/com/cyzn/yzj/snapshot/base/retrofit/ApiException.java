package com.cyzn.yzj.snapshot.base.retrofit;

/**
 * @author YZJ
 * @description 自定义exception 用于访问Api
 * @date 2018/10/19
 */
public class ApiException extends RuntimeException {

    private int code;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(String message) {
        super(new Throwable(message));

    }
}