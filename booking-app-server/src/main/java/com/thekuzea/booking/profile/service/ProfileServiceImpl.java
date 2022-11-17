package com.thekuzea.booking.profile.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thekuzea.booking.api.dto.PrivilegeGroupResource;
import com.thekuzea.booking.api.dto.ProfilePageResource;
import com.thekuzea.booking.api.dto.ProfileResource;
import com.thekuzea.booking.api.dto.RegisterProfileResource;
import com.thekuzea.booking.infrastructure.persistence.ProfileRepository;
import com.thekuzea.booking.infrastructure.security.service.PasswordEncoderService;
import com.thekuzea.booking.profile.domain.mapper.ProfileMapper;
import com.thekuzea.booking.profile.domain.model.Profile;
import com.thekuzea.booking.support.alert.AlertCode;
import com.thekuzea.booking.support.alert.AlertService;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    private final ProfileRepository profileRepository;

    private final PrivilegeGroupService privilegeGroupService;

    private final PasswordEncoderService passwordEncoderService;

    private final AlertService alertService;

    @Override
    public ProfileResource getProfileByUsername(final String username, final boolean isDetailed) {
        final Profile profile = validateAndGetProfileByUsername(username);
        final ProfileResource resource = profileMapper.modelToResource(profile);

        if (isDetailed) {
            final PrivilegeGroupResource privilegeGroupResource =
                    privilegeGroupService.getPrivilegeGroupById(profile.getPrivilegeGroupId());

            resource.setPrivilegeGroup(privilegeGroupResource);
        }

        return resource;
    }

    @Override
    public List<ProfileResource> getProfilesByIds(final List<String> profileIds) {
        return profileRepository.findAllById(profileIds)
                .stream()
                .map(profileMapper::modelToResource)
                .collect(Collectors.toList());
    }

    @Override
    public ProfilePageResource getAllProfilesByParameters(final int size, final int page) {
        final Pageable pageRequest = PageRequest.of(page, size);
        final Page<Profile> profilePage = profileRepository.findAll(pageRequest);

        final List<ProfileResource> profileResources = profilePage.getContent()
                .stream()
                .map(profileMapper::modelToResource)
                .collect(Collectors.toList());

        return new ProfilePageResource()
                .size(size)
                .page(page)
                .total(profilePage.getTotalElements())
                .profiles(profileResources);
    }

    @Override
    public void registerNewProfile(final RegisterProfileResource profileResource) {
        final boolean profileExists = profileRepository.existsByUsername(profileResource.getUsername());
        if (profileExists) {
            final String errorMessage = alertService.logAlertByCode(AlertCode.B001, LogLevel.WARN);
            throw new IllegalArgumentException(errorMessage);
        }

        validatePasswords(profileResource);

        final Profile profile = profileMapper.registerResourceToModel(profileResource);
        final String encryptedPassword = passwordEncoderService.encodePassword(profileResource.getPassword());

        profile.setPassword(encryptedPassword);
        profile.setPrivilegeGroupId(privilegeGroupService.getDefaultPrivilegeGroupId());

        profileRepository.save(profile);
    }

    @Override
    public void updateProfileByUsername(final String username, final ProfileResource profileResource) {
        final Profile profile = validateAndGetProfileByUsername(username);
        profile.setFirstName(profileResource.getFirstName());
        profile.setLastName(profileResource.getLastName());
        profile.setCountryCode(profileResource.getCountryCode());
        profile.setCity(profileResource.getCity());
        profile.setPhoneNumber(profileResource.getPhoneNumber());

        profileRepository.save(profile);
    }

    private Profile validateAndGetProfileByUsername(final String username) {
        final Optional<Profile> foundProfile = profileRepository.findByUsername(username);
        if (foundProfile.isPresent()) {
            return foundProfile.get();
        }

        final String errorMessage = alertService.logAlertByCode(AlertCode.B002, LogLevel.WARN, username);
        throw new IllegalArgumentException(errorMessage);
    }

    private void validatePasswords(final RegisterProfileResource resource) {
        final String general = resource.getPassword();
        final String confirmation = resource.getConfirmPassword();

        if (!general.equals(confirmation)) {
            final String errorMessage = alertService.logAlertByCode(AlertCode.S003, LogLevel.WARN);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
