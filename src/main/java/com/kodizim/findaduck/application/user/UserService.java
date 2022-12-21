package com.kodizim.findaduck.application.user;


import com.auth0.client.auth.AuthAPI;
import com.auth0.json.mgmt.users.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kodizim.findaduck.api.user.UserController;
import com.kodizim.findaduck.domain.UserType;
import com.kodizim.findaduck.infrastructure.auth0.Auth0Properties;
import com.kodizim.findaduck.infrastructure.auth0.ManagementApiWrapper;
import com.kodizim.findaduck.domain.AddUserCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class UserService {

    private final ManagementApiWrapper apiWrapper;

    private final Auth0Properties properties;

    private final EmployeeService employeeService;

    private final CompanyService companyService;

    private final RestTemplate restTemplate;

    public UserService(AuthAPI api, Auth0Properties properties, EmployeeService employeeService, CompanyService companyService, RestTemplateBuilder restTemplateBuilder) {
        this.properties = properties;
        var objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.restTemplate = restTemplateBuilder
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
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
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Auth0TokenEntity{
        String accessToken;
        String idToken;
        String Scope;
        Integer expiresIn;
        String tokenType;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Auth0TokenRequest{
        String grantType;
        String realm;
        String clientId;
        String clientSecret;
        String username;
        String password;
        String scope;
        String audience;
    }
    public UserController.UserToken login(String email, String password) {
        var uri = UriComponentsBuilder.fromUriString("https://kodforum.us.auth0.com/oauth/token")
                .build().toUri();
        var request = new Auth0TokenRequest(
                "http://auth0.com/oauth/grant-type/password-realm",
                "Username-Password-Authentication",
                properties.getApiClientId(),
                properties.getApiClientSecret(),
                email,
                password,
                "profile email openid",
                properties.getAudience()
        );
        Auth0TokenEntity token = restTemplate.postForObject(uri,request,Auth0TokenEntity.class);

        return new UserController.UserToken(token.accessToken);
    }
}
