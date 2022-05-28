package com.thekuzea.booking.infrastructure.security.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.thekuzea.booking.profile.domain.model.Profile;

public interface TokenService {

    String generateToken(final Profile profile);

    String getUsernameFromToken(final String token);

    boolean validateToken(final String token, final UserDetails userDetails);
}
