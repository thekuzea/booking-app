package com.thekuzea.booking.profile.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thekuzea.booking.api.AuthApi;
import com.thekuzea.booking.api.dto.LoginProfileResource;
import com.thekuzea.booking.api.dto.TokenResource;
import com.thekuzea.booking.infrastructure.security.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthApi {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<TokenResource> authenticate(final LoginProfileResource loginProfileResource) {
        final String token = authenticationService.authenticate(loginProfileResource);
        return ResponseEntity.accepted().body(new TokenResource().token(token));
    }
}
