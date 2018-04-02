package com.cdvtech.talibanairlines.controller

import com.cdvtech.talibanairlines.config.JwtTokenUtil
import com.cdvtech.talibanairlines.model.entity.AuthToken
import com.cdvtech.talibanairlines.model.entity.LoginUser
import com.cdvtech.talibanairlines.model.entity.Profile
import com.cdvtech.talibanairlines.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/token")
class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager

    @Autowired
    JwtTokenUtil jwtTokenUtil

    @Autowired
    ProfileRepository profileRepository

    @GetMapping(value = "/generate-token")
    ResponseEntity<?> check() {
        return ResponseEntity.ok("Access - yes")
    }

    @PostMapping(value = "/generate-token")
    ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        )

        SecurityContextHolder.getContext().setAuthentication(authentication)

        final Profile profile = profileRepository.findById(loginUser.getUsername()).get()
        final String token = jwtTokenUtil.generateToken(profile)

        return ResponseEntity.ok(new AuthToken(token))
    }

}
