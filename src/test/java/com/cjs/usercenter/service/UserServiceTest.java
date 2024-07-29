package com.cjs.usercenter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUserName("Lucy");
        user.setUserAccount("123456");
        user.setAvatarUrl("");
        user.setGender(1);
        user.setUserPassword("123456");
        user.setPhone("12345678");
        user.setEmail("123@123.com");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "aaaaaa";
        String password = "";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "aaa";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "aaaaaa";
        password = "123456";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "123456";
        password="12345678";
        checkPassword="12345678";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "aaa+aaa";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "aaaaaa";
        password = "123456789";
        result = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "aaaaaa";
        password = "12345678";
        checkPassword="12345678";
        result = userService.userRegister(userAccount, password, checkPassword);
        assertTrue(result > 0);
    }
}