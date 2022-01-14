package com.thekuzea.booking.profile.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "profiles")
class Profile {

    @Id
    String id

    String username

    String firstName

    String lastName

    String email

    String password

    String phoneNumber

    String countryCode

    String city

    String privilegeGroupId
}
