package com.cjs.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjs.usercenter.model.domain.User;
import com.cjs.usercenter.service.UserService;
import com.cjs.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.cjs.usercenter.contant.UserContant.ADMIN_ROLE;
import static com.cjs.usercenter.contant.UserContant.USER_LOGIN_STATUS;

/**
 * @author 0102
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2024-07-25 16:42:50
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值
     */
    private static final String SALT = "cjs";

    @Override
    public long userRegister(String userAccount, String password, String checkPassword,String planetCode) {
        //1.校验数据
        //校验是否为空
        if (StringUtils.isAnyBlank(userAccount, password, checkPassword)) {
            return -1;
        }
        //检验账户位数
        if (userAccount.length() < 6) {
            return -1;
        }
        //检验密码位数
        if (password.length() < 8) {
            return -1;
        }
        //检验密码与确认密码是否一致
        if (!checkPassword.equals(password)) {
            return -1;
        }
        if (planetCode.length()>5){
            return -1;
        }
        //不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //检验账户是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //检验星球编号是否重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        //2.数据加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        int insert = userMapper.insert(user);
        if (insert < 1) {
            return -1;
        }

        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        //1.校验数据
        //校验是否为空
        if (StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }
        //检验账户位数
        if (userAccount.length() < 6) {
            return null;
        }
        //检验密码位数
        if (password.length() < 8) {
            return null;
        }
        //不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

        //2.数据加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user == null) {
            log.info("user login failed,userAccount cannot match password");
            return null;
        }

        //3.用户脱敏
        User safetyUser=getSafetyUser(user);

        //4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATUS, safetyUser);


        return safetyUser;
    }

    @Override
    public boolean deleteUser(long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public List<User> searchUser(String userName, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        return userList.stream().map(user -> {
            return getSafetyUser(user);
        }).collect(Collectors.toList());
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) attribute;
        return user != null || user.getUserRole() == ADMIN_ROLE;
    }

    public User getSafetyUser(User originalUser) {
        if (originalUser==null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originalUser.getId());
        safetyUser.setUserName(originalUser.getUserName());
        safetyUser.setUserAccount(originalUser.getUserAccount());
        safetyUser.setAvatarUrl(originalUser.getAvatarUrl());
        safetyUser.setGender(originalUser.getGender());
        safetyUser.setPhone(originalUser.getPhone());
        safetyUser.setEmail(originalUser.getEmail());
        safetyUser.setPlanetCode(originalUser.getPlanetCode());
        safetyUser.setUserRole(originalUser.getUserRole());
        safetyUser.setUserStatus(originalUser.getUserStatus());
        safetyUser.setCreateTime(originalUser.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogOut(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 1;
    }


}




