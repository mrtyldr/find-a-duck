package com.kodizim.kodforum.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends AbstractAggregateRoot<User> {
    @Id
    private String userId;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;


}
