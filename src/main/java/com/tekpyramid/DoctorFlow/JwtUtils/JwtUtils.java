package com.tekpyramid.DoctorFlow.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    // You can externalize this to application.properties later
    private static final String SECRET_KEY = "your-very-secure-and-long-secret-key-for-jwt-signing";

    // JJWT recommends key length >= 256 bits for HS256
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /* 1. Generate JWT token */
    public String generateToken(String subject) throws IOException {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Aarthy") // you can customize this
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    /* 2. Extract claims */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /* 3. Extract expiration date */
    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    /* 4. Extract username (subject) */
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    /* 5. Check if token expired */
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    /* 6. Validate token */
    public boolean validateToken(String token, String username) {
        String usernameFromToken = getUsername(token);
        return username.equals(usernameFromToken) && !isTokenExpired(token);
    }
}
