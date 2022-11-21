package com.kodizim.kodforum.infrastructure.auth0;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserProfile {

    private String email;
    private String phoneNumber;
    private String username;
    private String picture;
    private String givenName;
    private String familyName;
    private Integer loginsCount;
}
