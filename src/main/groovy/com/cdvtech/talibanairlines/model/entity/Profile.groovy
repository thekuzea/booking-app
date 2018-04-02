package com.cdvtech.talibanairlines.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotNull

@Document(collection = "profiles")
class Profile {

    @Id
    String username

    @NotNull
    String firstName

    @NotNull
    String lastName

    @NotNull
    String email

    @JsonIgnore
    @NotNull
    String password

    @Transient
    String confirmPassword

    @NotNull
    String phoneNumber

    @NotNull
    String role

}
