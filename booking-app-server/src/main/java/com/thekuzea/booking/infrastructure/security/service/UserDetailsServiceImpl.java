package com.thekuzea.booking.infrastructure.security.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.thekuzea.booking.infrastructure.persistence.ProfileRepository;
import com.thekuzea.booking.profile.domain.model.Profile;
import com.thekuzea.booking.profile.service.PrivilegeGroupService;
import com.thekuzea.booking.support.alert.AlertCode;
import com.thekuzea.booking.support.alert.AlertService;
import com.thekuzea.booking.support.util.PrivilegeConverter;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ProfileRepository profileRepository;

    private final AlertService alertService;

    private final PrivilegeGroupService privilegeGroupService;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Optional<Profile> foundProfile = profileRepository.findByUsername(username);

        if (!foundProfile.isPresent()) {
            alertService.logAndThrowException(AlertCode.S001, IllegalArgumentException.class, LogLevel.WARN);
        }

        final Profile profile = foundProfile.get();
        final List<String> privileges = privilegeGroupService.getPrivilegesById(profile.getPrivilegeGroupId());
        return new User(profile.getUsername(), profile.getPassword(), PrivilegeConverter.privilegesToAuthorities(privileges));
    }
}
