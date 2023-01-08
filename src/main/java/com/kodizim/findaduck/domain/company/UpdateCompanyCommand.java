package com.kodizim.findaduck.domain.company;

import javax.validation.constraints.NotNull;

public record UpdateCompanyCommand(
        @NotNull String companyName,
        @NotNull String phoneNumber,
        String about,
        String photoLocationKey
) {
}
