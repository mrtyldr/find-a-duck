package com.kodizim.findaduck.infrastructure.auth0;

public interface ExternalUserProfileRepository {

    UserProfile getUserProfileWithUserId(String userId);
}
