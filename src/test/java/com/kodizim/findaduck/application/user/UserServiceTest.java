package com.kodizim.findaduck.application.user;

import com.kodizim.findaduck.BaseTestClass;
import com.kodizim.findaduck.api.user.UserController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends BaseTestClass {
    @Autowired
    UserService userService;

}