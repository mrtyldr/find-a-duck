package com.kodizim.kodforum.application.user;


import com.auth0.client.auth.AuthAPI;
import com.auth0.json.mgmt.users.User;
import com.kodizim.kodforum.domain.UserType;
import com.kodizim.kodforum.infrastructure.auth0.Auth0Properties;
import com.kodizim.kodforum.infrastructure.auth0.ManagementApiWrapper;
import com.kodizim.kodforum.domain.AddUserCommand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    private final ManagementApiWrapper apiWrapper;

    private final Auth0Properties properties;

    private final EmployeeService employeeService;

    private final CompanyService companyService;

    public UserService(AuthAPI api, Auth0Properties properties, EmployeeService employeeService, CompanyService companyService) {
        this.properties = properties;
        this.employeeService = employeeService;
        this.companyService = companyService;
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
            if(command.getUserType().equals(UserType.EMPLOYEE))
                employeeService.addEmployeeUser(createdUser.getId(),command.getEmail());
            else
                companyService.addCompanyUser(createdUser.getId(),command.getEmail());
        } catch (Exception e) {
            log.info("düştü buraya");
            throw new RuntimeException("exception occured");
        }
    }
}
