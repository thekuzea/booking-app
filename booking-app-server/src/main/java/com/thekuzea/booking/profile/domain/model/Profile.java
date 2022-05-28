package com.thekuzea.booking.profile.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "profiles")
public class Profile {

    @Id
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private String countryCode;

    private String city;

    private String privilegeGroupId;
}
