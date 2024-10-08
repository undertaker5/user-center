package com.cjs.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author cjs
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 261378436156984641L;

    private String userAccount;

    private String password;
}
