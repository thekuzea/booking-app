package com.thekuzea.booking.infrastructure.security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordEncoderServiceImpl implements PasswordEncoderService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder

    @Override
    String encodePassword(final String password) {
        bCryptPasswordEncoder.encode(password)
    }
}
