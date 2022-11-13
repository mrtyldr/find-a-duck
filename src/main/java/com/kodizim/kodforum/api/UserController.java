package com.kodizim.kodforum.api;


import com.kodizim.kodforum.service.UserService;
import com.kodizim.kodforum.service.domain.AddUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/")
public class UserController {

    private final UserService userService;

    @PostMapping("users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addUser(@RequestBody AddUserCommand command){
        userService.addUser(command);
    }



}
