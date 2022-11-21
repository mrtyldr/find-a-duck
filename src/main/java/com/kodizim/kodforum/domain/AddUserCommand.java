package com.kodizim.kodforum.domain;

import lombok.Data;
import lombok.Value;

@Data
public class AddUserCommand {
    private String email;
    private String password;
}
