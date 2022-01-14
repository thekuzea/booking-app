package com.thekuzea.booking.profile.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

import com.thekuzea.booking.api.dto.PrivilegeGroupResource
import com.thekuzea.booking.api.dto.ProfilePageResource
import com.thekuzea.booking.api.dto.ProfileResource
import com.thekuzea.booking.api.dto.RegisterProfileResource
import com.thekuzea.booking.infrastructure.persistence.ProfileRepository
import com.thekuzea.booking.infrastructure.security.service.PasswordEncoderService
import com.thekuzea.booking.profile.domain.mapper.ProfileMapper
import com.thekuzea.booking.profile.domain.model.Profile
import com.thekuzea.booking.support.alert.AlertCode
import com.thekuzea.booking.support.alert.AlertService

@Service
class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository

    @Autowired
    PrivilegeGroupService privilegeGroupService

    @Autowired
    PasswordEncoderService passwordEncoderService

    @Autowired
    AlertService alertService

    @Override
    ProfileResource getProfileByUsername(final String username, final boolean isDetailed) {
        final Profile profile = validateAndGetProfileByUsername(username)
        final ProfileResource resource = ProfileMapper.modelToResource(profile)

        if (isDetailed) {
            final PrivilegeGroupResource privilegeGroupResource =
                    privilegeGroupService.getPrivilegeGroupById(profile.getPrivilegeGroupId())

            resource.setPrivilegeGroup(privilegeGroupResource)
        }

        resource
    }

    @Override
    List<ProfileResource> getProfilesByIds(final List<String> profileIds) {
        profileRepository.findAllById(profileIds)
                .collect { ProfileMapper.modelToResource(it) }
    }

    @Override
    ProfilePageResource getAllProfilesByParameters(final int size, final int page) {
        final Pageable pageRequest = PageRequest.of(page, size)
        final Page<Profile> profilePage = profileRepository.findAll(pageRequest)

        final List<ProfileResource> profileResources = profilePage.getContent()
                .collect { ProfileMapper.modelToResource(it) }

        new ProfilePageResource()
                .size(size)
                .page(page)
                .total(profilePage.getTotalElements())
                .profiles(profileResources)
    }

    @Override
    void registerNewProfile(final RegisterProfileResource profileResource) {
        final boolean profileExists = profileRepository.existsByUsername(profileResource.getUsername())
        if (profileExists) {
            alertService.logAndThrowException(AlertCode.B001, IllegalArgumentException.class, LogLevel.WARN)
        }

        validatePasswords(profileResource)

        final Profile profile = ProfileMapper.registerResourceToModel(profileResource)
        final String encryptedPassword = passwordEncoderService.encodePassword(profileResource.getPassword())

        profile.setPassword(encryptedPassword)
        profile.setPrivilegeGroupId(privilegeGroupService.getDefaultPrivilegeGroupId())

        profileRepository.save(profile)
    }

    @Override
    void updateProfileByUsername(final String username, final ProfileResource profileResource) {
        final Profile profile = validateAndGetProfileByUsername(username)
        profile.setFirstName(profileResource.getFirstName())
        profile.setLastName(profileResource.getLastName())
        profile.setCountryCode(profileResource.getCountryCode())
        profile.setCity(profileResource.getCity())
        profile.setPhoneNumber(profileResource.getPhoneNumber())

        profileRepository.save(profile)
    }

    private Profile validateAndGetProfileByUsername(final String username) {
        final Optional<Profile> foundProfile = profileRepository.findByUsername(username)
        if (!foundProfile.isPresent()) {
            alertService.logAndThrowException(AlertCode.B002, IllegalArgumentException.class, LogLevel.WARN, username)
        }

        foundProfile.get()
    }

    private void validatePasswords(final RegisterProfileResource resource) {
        final String general = resource.getPassword()
        final String confirmation = resource.getConfirmPassword()

        if (general != confirmation) {
            alertService.logAndThrowException(AlertCode.S003, IllegalArgumentException.class, LogLevel.WARN)
        }
    }
}
