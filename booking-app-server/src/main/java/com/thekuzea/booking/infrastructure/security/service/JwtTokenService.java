package com.thekuzea.booking.infrastructure.security.service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.thekuzea.booking.profile.domain.model.Profile;
import com.thekuzea.booking.profile.service.PrivilegeGroupService;
import com.thekuzea.booking.support.util.PrivilegeConverter;

@Service
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

    private static final String SCOPES = "scopes";

    private final PrivilegeGroupService privilegeGroupService;

    @Value("#{new Integer(${booking-app.settings.security.jwt.access-validity-token-seconds})}")
    private int accessValidityTokenSeconds;

    @Value("${booking-app.settings.security.jwt.issuer}")
    private String issuer;

    @Value("${booking-app.settings.security.jwt.signing-key}")
    private String signingKey;

    @Override
    public String generateToken(final Profile profile) {
        return doGenerateToken(profile);
    }

    @Override
    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(final String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    private String doGenerateToken(final Profile profile) {
        final Claims claims = Jwts.claims().setSubject(profile.getUsername());
        final List<String> privileges = privilegeGroupService.getPrivilegesById(profile.getPrivilegeGroupId());
        claims.put(SCOPES, PrivilegeConverter.privilegesToAuthorities(privileges));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessValidityTokenSeconds * 1000L))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }
}
