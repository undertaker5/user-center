package com.cjs.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 同意返回类
 *
 * @param <T>
 * @author cjs
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private T data;
    private String msg;

    public BaseResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }
}
