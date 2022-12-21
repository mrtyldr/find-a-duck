package com.kodizim.findaduck.application.user;

import com.kodizim.findaduck.BaseTestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends BaseTestClass {
    @Autowired
    UserService userService;

    @Test
    void login() {
        userService.login("admin@kodforum.com","123456789");
    }
}