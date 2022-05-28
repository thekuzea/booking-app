package com.thekuzea.booking.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String encodePassword(final String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
