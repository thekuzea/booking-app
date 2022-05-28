package com.thekuzea.booking.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.thekuzea.booking.api.dto.LoginProfileResource;
import com.thekuzea.booking.infrastructure.persistence.ProfileRepository;
import com.thekuzea.booking.profile.domain.model.Profile;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final ProfileRepository profileRepository;

    private final TokenService jwtTokenService;

    @Override
    public String authenticate(final LoginProfileResource loginProfileResource) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginProfileResource.getUsername(),
                        loginProfileResource.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final Profile profile = profileRepository.findByUsername(loginProfileResource.getUsername()).get();
        return jwtTokenService.generateToken(profile);
    }
}
