package com.kodizim.findaduck.api.user;


import com.kodizim.findaduck.api.model.Response;
import com.kodizim.findaduck.application.user.UserService;
import com.kodizim.findaduck.domain.AddUserCommand;
import com.kodizim.findaduck.domain.UserInfo;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addUser(@RequestBody AddUserCommand command){
        userService.addUser(command);
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserToken{
        String accessToken;
    }
    @Value
    public static class LoginCommand {
        String email;
        String password;
    }
    @PostMapping("login")
    Response<UserToken> login(@RequestBody LoginCommand command){
        return Response.of(userService.login(command.getEmail(),command.getPassword()));
    }

    @GetMapping("user-info")
    Response<UserInfo> getUser(Principal principal){
        return Response.of(userService.getUserInfo(principal.getName()));
    }

}
