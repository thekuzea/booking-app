package com.thekuzea.booking.infrastructure.security

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import groovy.util.logging.Slf4j
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.logging.LogLevel
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import com.thekuzea.booking.infrastructure.security.service.TokenService
import com.thekuzea.booking.support.alert.AlertCode
import com.thekuzea.booking.support.alert.AlertService

@Slf4j
@Component
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String EMPTY_STRING = ""

    private static final String TOKEN_PREFIX = "Bearer "

    private static final String HEADER_STRING = "Authorization"

    @Autowired
    UserDetailsService jwtUserDetailsService

    @Autowired
    TokenService jwtTokenService

    @Autowired
    AlertService alertService

    @Override
    protected void doFilterInternal(final HttpServletRequest req,
                                    final HttpServletResponse res,
                                    final FilterChain chain) throws IOException, ServletException {

        final String header = req.getHeader(HEADER_STRING)
        String username = null
        String authToken = null

        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, EMPTY_STRING)

            try {
                username = jwtTokenService.getUsernameFromToken(authToken)
            } catch (IllegalArgumentException e) {
                alertService.logAndRethrowException(AlertCode.S002, e, LogLevel.ERROR)
            } catch (ExpiredJwtException e) {
                log.debug("Token expired", e)
            } catch (SignatureException e) {
                log.debug("Authentication Failed. Username or Password not valid.", e)
            }
        } else {
            log.debug("Skipping header without Bearer token")
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username)

            if (jwtTokenService.validateToken(authToken, userDetails)) {
                final UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req))
                log.debug("Authenticated user {} is being set in security context", username)

                SecurityContextHolder.getContext().setAuthentication(authentication)
            }
        }

        chain.doFilter(req, res)
    }
}
