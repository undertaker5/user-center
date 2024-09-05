package com.cjs.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjs.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 0102
 * @description 针对表【t_user】的数据库操作Service
 * @createDate 2024-07-25 16:42:50
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param password      用户密码
     * @param checkPassword 校验密码
     * @param planetCode    星球编号
     * @return 新用户id
     */
    long userRegister(String userAccount, String password, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount 用户账户
     * @param password    用户密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String password, HttpServletRequest request);

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    List<User> searchUser(String userName, HttpServletRequest request);

    boolean deleteUser(long id, HttpServletRequest request);

    User getSafetyUser(User originalUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogOut(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList 用户需要的标签
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);
}
