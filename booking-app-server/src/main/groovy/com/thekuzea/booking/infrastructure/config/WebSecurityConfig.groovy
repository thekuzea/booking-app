package com.thekuzea.booking.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig {

    @Bean
    BCryptPasswordEncoder encoder() {
        new BCryptPasswordEncoder()
    }

    @Bean
    AuthenticationManager authenticationManagerBean(final WebSecurityConfigurerAdapter adapter) {
        adapter.authenticationManagerBean()
    }
}
