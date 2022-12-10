package com.kodizim.findaduck.infrastructure.auth0;


import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import static java.util.Locale.ENGLISH;
import static java.util.concurrent.TimeUnit.SECONDS;

@RequiredArgsConstructor
public class ManagementApiWrapper {
    private final AuthAPI authAPI;
    private final String audience;
    private final String domain;

    private ManagementAPI managementAPI;
    private final Object lock = new Object();
    private long lastUpdate;

    private void init() throws Auth0Exception {
        synchronized (lock) {
            if (managementAPI == null) {
                var token = authAPI.requestToken(audience).execute();
                managementAPI = new ManagementAPI(domain, token.getAccessToken());
                lastUpdate = System.currentTimeMillis();
            }
        }
    }

    @SneakyThrows
    public <Result> Result call(Call<Result> consumer) {
        try {
            if (managementAPI == null)
                init();
            return consumer.accept(managementAPI);
        } catch (APIException e) {
            if (e.getStatusCode() == 401 && e.getMessage().toLowerCase(ENGLISH).contains("expired")) {
                synchronized (lock) {
                    if (!recentlyUpdated()) {
                        var token = authAPI.requestToken(audience).execute();
                        managementAPI.setApiToken(token.getAccessToken());
                        lastUpdate = System.currentTimeMillis();
                    }

                }
                return consumer.accept(managementAPI);
            } else
                throw e;

        }
    }

    private boolean recentlyUpdated() {
        long now = System.currentTimeMillis();
        long recencyLimit = SECONDS.toMillis(10);
        return now - lastUpdate <= recencyLimit;
    }

}
