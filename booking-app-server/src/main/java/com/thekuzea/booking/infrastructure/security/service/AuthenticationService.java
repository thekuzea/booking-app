package com.thekuzea.booking.infrastructure.security.service;

import com.thekuzea.booking.api.dto.LoginProfileResource;

public interface AuthenticationService {

    String authenticate(LoginProfileResource loginProfileResource);
}
