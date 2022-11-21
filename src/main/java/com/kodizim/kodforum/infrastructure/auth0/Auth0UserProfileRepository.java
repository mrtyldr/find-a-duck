package com.kodizim.kodforum.infrastructure.auth0;

import com.auth0.client.auth.AuthAPI;
import com.auth0.json.mgmt.users.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Auth0UserProfileRepository implements ExternalUserProfileRepository {
    private final ManagementApiWrapper managementApiWrapper;

    public Auth0UserProfileRepository(AuthAPI authAPI, Auth0Properties properties) {
        this.managementApiWrapper = new ManagementApiWrapper(
                authAPI,
                properties.getManagementAudience(),
                properties.getDomain());
    }

    @Override
    public UserProfile getUserProfileWithUserId(String userId) {
        var user = getUser(userId);
        return toUserProfile(user);
    }

    private UserProfile toUserProfile(User user) {
        return UserProfile.builder()
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .picture(user.getPicture())
                .username(user.getUsername())
                .givenName(user.getGivenName())
                .familyName(user.getFamilyName())
                .loginsCount(user.getLoginsCount())
                .build();
    }

    private User getUser(String userId) {
        return managementApiWrapper.call(api -> api.users().get(userId,null).execute());
    }
}
