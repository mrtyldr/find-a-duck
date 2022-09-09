package com.kodizim.kodforum.security;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;

@FunctionalInterface
public interface Call<Result> {
    Result accept(ManagementAPI m) throws Auth0Exception;
}
