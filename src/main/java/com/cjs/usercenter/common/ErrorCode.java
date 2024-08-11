package com.cjs.usercenter.common;

/**
 * 错误码
 *
 * @author cjs
 */

public enum ErrorCode {
    SUCCESS(200,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NO_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"无权限",""),
    SYSTEM_ERROR(50000,"系统内部异常","");

    /**
     * 状态码
     */
    private final int code;
    /**
     * 状态码信息
     */
    private final String msg;
    /**
     * 状态码描述
     */
    private final String description;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDescription() {
        return description;
    }

    ErrorCode(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }
}
