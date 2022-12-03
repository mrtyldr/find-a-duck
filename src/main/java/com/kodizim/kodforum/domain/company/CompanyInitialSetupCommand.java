package com.kodizim.kodforum.domain.company;


import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CompanyInitialSetupCommand {
    @NotNull
    String companyName;
    @NotNull
    String phoneNumber;

    String about;

    String photoLocationKey;

    String addressLine;

    String city;

    String country;

    String postalCode;

}
