package com.cjs.usercenter.common;

/**
 * 返回工具类
 *
 * @author cjs
 */
public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(200,data,"ok");
    }
}
