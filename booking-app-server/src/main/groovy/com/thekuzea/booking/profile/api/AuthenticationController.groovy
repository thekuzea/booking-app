package com.thekuzea.booking.profile.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

import com.thekuzea.booking.api.AuthApi
import com.thekuzea.booking.api.dto.LoginProfileResource
import com.thekuzea.booking.api.dto.TokenResource

import com.thekuzea.booking.infrastructure.security.service.AuthenticationService

@RestController
class AuthenticationController implements AuthApi {

    @Autowired
    AuthenticationService authenticationService

    @Override
    ResponseEntity<TokenResource> authenticate(final LoginProfileResource loginProfileResource) {
        final String token = authenticationService.authenticate(loginProfileResource)
        ResponseEntity.accepted().body(new TokenResource().token(token))
    }
}
