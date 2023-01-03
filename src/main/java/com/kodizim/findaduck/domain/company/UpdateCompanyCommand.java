package com.kodizim.findaduck.domain.company;

public record UpdateCompanyCommand(
        String companyName,
        String phoneNumber,
        String about,
        String photoLocationKey
) {
}
