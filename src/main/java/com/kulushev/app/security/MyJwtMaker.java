package com.kulushev.app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class MyJwtMaker {

    private SecretKey key;

    public MyJwtMaker() {
        String secret  = "super_mega_slozhniy_secret_key_?hfy^7jY5$9_877&*^^%jhjhyjhHGH";
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String,Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }

    public String extractLogin(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsFunction) {
        return claimsFunction.apply(Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload());
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        String login = extractLogin(token);
        return login.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}
