package com.kodizim.findaduck.domain.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Company extends AbstractAggregateRoot<Company> {
    @Id
    private String companyId;

    private String companyName;

    private String phoneNumber;

    private String email;

    private String about;

    private String photoLocationKey;

    private String addressLine;

    private String city;

    private String country;

    private String postalCode;

    public Company(String companyId, String email) {
        this.companyId = companyId;
        this.email = email;
    }

    public void companyInitial(String companyName,
                               String phoneNumber,
                               String about,
                               String photoLocationKey,
                               String addressLine,
                               String city,
                               String country,
                               String postalCode
    ) {
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.about = about;
        this.photoLocationKey= photoLocationKey;
        this.addressLine = addressLine;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }
}
