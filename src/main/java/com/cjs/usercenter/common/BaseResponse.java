package com.cjs.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回类
 *
 * @param <T>
 * @author cjs
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private T data;
    private String msg;
    private String description;

    public BaseResponse(int code, T data, String msg, String description) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.description = description;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public BaseResponse(int code, T data, String msg) {
        this(code, data, null, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMsg(), errorCode.getDescription());
    }
}
