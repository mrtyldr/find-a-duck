package com.kodizim.kodforum.domain;

import lombok.Data;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Value
public class AddUserCommand {
    @NotNull
    @Email
    String email;
    @NotNull
    @Length(max = 20,min = 5)
    String password;
    @NotNull
    UserType userType;

}
