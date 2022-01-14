package com.thekuzea.booking.infrastructure.security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

import com.thekuzea.booking.api.dto.LoginProfileResource
import com.thekuzea.booking.profile.domain.model.Profile
import com.thekuzea.booking.infrastructure.persistence.ProfileRepository

@Service
class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager

    @Autowired
    ProfileRepository profileRepository

    @Autowired
    TokenService jwtTokenService

    @Override
    String authenticate(final LoginProfileResource loginProfileResource) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginProfileResource.getUsername(),
                        loginProfileResource.getPassword()
                )
        )

        SecurityContextHolder.getContext().setAuthentication(authentication)

        final Profile profile = profileRepository.findByUsername(loginProfileResource.getUsername()).get()
        jwtTokenService.generateToken(profile)
    }
}
