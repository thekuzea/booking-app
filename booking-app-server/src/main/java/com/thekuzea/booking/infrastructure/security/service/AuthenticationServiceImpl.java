package com.thekuzea.booking.infrastructure.security.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.thekuzea.booking.api.dto.LoginProfileResource;
import com.thekuzea.booking.infrastructure.persistence.ProfileRepository;
import com.thekuzea.booking.profile.domain.model.Profile;
import com.thekuzea.booking.support.alert.AlertCode;
import com.thekuzea.booking.support.alert.AlertService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final ProfileRepository profileRepository;

    private final TokenService jwtTokenService;

    private final AlertService alertService;

    @Override
    public String authenticate(final LoginProfileResource loginProfileResource) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginProfileResource.getUsername(),
                        loginProfileResource.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final Optional<Profile> foundProfile = profileRepository.findByUsername(loginProfileResource.getUsername());
        if (!foundProfile.isPresent()) {
            final String errorMessage = alertService.logAlertByCode(AlertCode.S001, LogLevel.WARN);
            throw new IllegalArgumentException(errorMessage);
        }

        return jwtTokenService.generateToken(foundProfile.get());
    }
}
