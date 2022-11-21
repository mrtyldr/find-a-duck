package com.kodizim.kodforum.infrastructure.auth0;

public interface ExternalUserProfileRepository {

    UserProfile getUserProfileWithUserId(String userId);
}
