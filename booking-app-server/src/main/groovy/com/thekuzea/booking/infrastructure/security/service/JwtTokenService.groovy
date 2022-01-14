package com.thekuzea.booking.infrastructure.security.service

import java.util.function.Function

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

import com.thekuzea.booking.profile.domain.model.Profile
import com.thekuzea.booking.profile.service.PrivilegeGroupService
import com.thekuzea.booking.support.util.PrivilegeConverter

@Service
class JwtTokenService implements TokenService {

    private static final String SCOPES = "scopes"

    @Value('#{new Integer(${booking-app.settings.security.jwt.access-validity-token-seconds})}')
    private int accessValidityTokenSeconds

    @Value('${booking-app.settings.security.jwt.issuer}')
    private String issuer

    @Value('${booking-app.settings.security.jwt.signing-key}')
    private String signingKey

    @Autowired
    PrivilegeGroupService privilegeGroupService

    @Override
    String generateToken(final Profile profile) {
        doGenerateToken(profile)
    }

    @Override
    String getUsernameFromToken(final String token) {
        getClaimFromToken(token, { obj -> obj.getSubject() })
    }

    @Override
    boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token)
        (username == userDetails.getUsername() && !isTokenExpired(token))
    }

    private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token)
        claimsResolver.apply(claims)
    }

    private Claims getAllClaimsFromToken(final String token) {
        Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody()
    }

    private boolean isTokenExpired(final String token) {
        final Date expiration = getClaimFromToken(token, { obj -> obj.getExpiration() })
        expiration.before(new Date())
    }

    private String doGenerateToken(final Profile profile) {
        final Claims claims = Jwts.claims().setSubject(profile.getUsername())
        final List<String> privileges = privilegeGroupService.getPrivilegesById(profile.getPrivilegeGroupId())
        claims.put(SCOPES, PrivilegeConverter.privilegesToAuthorities(privileges))

        Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessValidityTokenSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact()
    }
}
