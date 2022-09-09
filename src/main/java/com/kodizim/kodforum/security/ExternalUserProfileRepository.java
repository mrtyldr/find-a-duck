package com.kodizim.kodforum.security;

public interface ExternalUserProfileRepository {

    UserProfile getUserProfileWithUserId(String userId);
}
