package com.kodizim.kodforum.service;


import com.auth0.client.auth.AuthAPI;
import com.auth0.json.mgmt.users.User;
import com.kodizim.kodforum.repository.UserRepository;
import com.kodizim.kodforum.security.Auth0Properties;
import com.kodizim.kodforum.security.ManagementApiWrapper;
import com.kodizim.kodforum.service.domain.AddUserCommand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    private final ManagementApiWrapper apiWrapper;

    private final Auth0Properties properties;

    public UserService(UserRepository userRepository, AuthAPI api, Auth0Properties properties) {
        this.userRepository = userRepository;
        this.properties = properties;
        this.apiWrapper = new ManagementApiWrapper(api
                , properties.getManagementAudience()
                , properties.getDomain());
    }

    @Transactional
    public void addUser(AddUserCommand command) {
        var user = new User();


        try {
            user.setConnection(properties.getUserDatabase());
            user.setEmail(command.getEmail());
            user.setPassword(command.getPassword().toCharArray());
            var createdUser = apiWrapper.call(api -> api.users().create(user).execute());
            var kodForumUser = new com.kodizim.kodforum.entity.User(
                    UUID.randomUUID(), createdUser.getId(), command.getEmail()
            );
            userRepository.save(kodForumUser);
        } catch (Exception e) {
            log.info("düştü buraya");
            throw new RuntimeException("exception occured");
        }
    }
}
