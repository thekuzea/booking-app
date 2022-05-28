package com.thekuzea.booking.profile.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thekuzea.booking.api.ProfileApi;
import com.thekuzea.booking.api.dto.ProfilePageResource;
import com.thekuzea.booking.api.dto.ProfileResource;
import com.thekuzea.booking.api.dto.RegisterProfileResource;
import com.thekuzea.booking.profile.service.ProfileService;

@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileApi {

    private final ProfileService profileService;

    @Override
    public ResponseEntity<ProfileResource> getProfileByUsername(final String username) {
        return ResponseEntity.ok(profileService.getProfileByUsername(username, false));
    }

    @Override
    public ResponseEntity<ProfileResource> getDetailedProfileByUsername(final String username) {
        return ResponseEntity.ok(profileService.getProfileByUsername(username, true));
    }

    @Override
    public ResponseEntity<ProfilePageResource> getAllProfilesByParameters(final Integer size, final Integer page) {
        return ResponseEntity.ok(profileService.getAllProfilesByParameters(size, page));
    }

    @Override
    public ResponseEntity<Void> registerNewProfile(final RegisterProfileResource profileResource) {
        profileService.registerNewProfile(profileResource);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> updateProfileByUsername(final String username, final ProfileResource profileResource) {
        profileService.updateProfileByUsername(username, profileResource);
        return ResponseEntity.accepted().build();
    }
}
