package com.thekuzea.booking.infrastructure.security

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    void commence(final HttpServletRequest request,
                  final HttpServletResponse response,
                  final AuthenticationException authException) throws IOException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}
