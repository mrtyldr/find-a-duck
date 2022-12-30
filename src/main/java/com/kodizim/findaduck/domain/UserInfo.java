package com.kodizim.findaduck.domain;

import lombok.Data;

@Data
public class UserInfo{

    String userId;
    UserType type;
    boolean ondboardingDone;

    public UserInfo(String userId, boolean ondboardingDone) {
        this.userId = userId;
        this.ondboardingDone = ondboardingDone;
    }
    public void setUserType(UserType type){
        this.type = type;
    }
}
