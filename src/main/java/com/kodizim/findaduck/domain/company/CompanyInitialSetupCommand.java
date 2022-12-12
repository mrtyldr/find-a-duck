package com.kodizim.findaduck.domain.company;




import javax.validation.constraints.NotNull;

public record CompanyInitialSetupCommand(@NotNull String companyName, @NotNull String phoneNumber, String about,
                                         String photoLocationKey, String addressLine, String city, String country,
                                         String postalCode) {
}
