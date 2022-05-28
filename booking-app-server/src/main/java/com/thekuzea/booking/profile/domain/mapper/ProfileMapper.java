package com.thekuzea.booking.profile.domain.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.thekuzea.booking.api.dto.ProfileResource;
import com.thekuzea.booking.api.dto.RegisterProfileResource;
import com.thekuzea.booking.profile.domain.model.Profile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProfileMapper {

    public static Profile registerResourceToModel(final RegisterProfileResource resource) {
        final Profile model = new Profile();
        model.setUsername(resource.getUsername());
        model.setFirstName(resource.getFirstName());
        model.setLastName(resource.getLastName());
        model.setEmail(resource.getEmail());
        model.setPassword(resource.getPassword());

        return model;
    }

    public static ProfileResource modelToResource(final Profile model) {
        return new ProfileResource()
                .id(model.getId())
                .username(model.getUsername())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .phoneNumber(model.getPhoneNumber())
                .countryCode(model.getCountryCode())
                .city(model.getCity());
    }
}
