package com.cdvtech.talibanairlines.config

import com.cdvtech.talibanairlines.model.entity.Constants
import com.cdvtech.talibanairlines.model.entity.Profile
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

import java.util.function.Function

@Component
class JwtTokenUtil implements Serializable {

    static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, { obj -> obj.getExpiration() })
    }

    static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, { obj -> obj.getSubject() })
    }

    static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Constants.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody()
    }

    static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token)
        return (username == userDetails.getUsername() && !isTokenExpired(token))
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token)
        return expiration.before(new Date())
    }

    static String generateToken(Profile profile) {
        return doGenerateToken(profile.getUsername())
    }

    private static String doGenerateToken(String subject) {

        Claims claims = Jwts.claims().setSubject(subject)
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://localhost:8080")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY)
                .compact()
    }

}
