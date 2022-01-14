package com.thekuzea.booking.profile.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import com.thekuzea.booking.api.ProfileApi
import com.thekuzea.booking.api.dto.ProfilePageResource
import com.thekuzea.booking.api.dto.ProfileResource
import com.thekuzea.booking.api.dto.RegisterProfileResource

import com.thekuzea.booking.profile.service.ProfileService

@RestController
class ProfileController implements ProfileApi {

    @Autowired
    ProfileService profileService

    @Override
    ResponseEntity<ProfileResource> getProfileByUsername(final String username) {
        ResponseEntity.ok(profileService.getProfileByUsername(username, false))
    }

    @Override
    ResponseEntity<ProfileResource> getDetailedProfileByUsername(final String username) {
        ResponseEntity.ok(profileService.getProfileByUsername(username, true))
    }

    @Override
    ResponseEntity<ProfilePageResource> getAllProfilesByParameters(final Integer size, final Integer page) {
        ResponseEntity.ok(profileService.getAllProfilesByParameters(size, page))
    }

    @Override
    ResponseEntity<Void> registerNewProfile(final RegisterProfileResource profileResource) {
        profileService.registerNewProfile(profileResource)
        ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @Override
    ResponseEntity<Void> updateProfileByUsername(final String username, final ProfileResource profileResource) {
        profileService.updateProfileByUsername(username, profileResource)
        ResponseEntity.accepted().build()
    }
}
