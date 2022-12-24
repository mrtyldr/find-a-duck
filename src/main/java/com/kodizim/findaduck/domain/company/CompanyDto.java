package com.kodizim.findaduck.domain.company;

public record CompanyDto(
        String companyId,
        String companyName,
        String about,
        String phoneNumber,
        String email,
        String photoLocationKey,
        String city

) {
}
