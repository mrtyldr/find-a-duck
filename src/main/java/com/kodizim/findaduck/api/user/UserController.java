package com.kodizim.findaduck.api.user;


import com.kodizim.findaduck.application.user.UserService;
import com.kodizim.findaduck.domain.AddUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    @PostMapping("users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addUser(@RequestBody AddUserCommand command){
        userService.addUser(command);
    }



}
