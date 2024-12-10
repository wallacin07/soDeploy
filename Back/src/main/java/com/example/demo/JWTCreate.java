package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import com.example.demo.DTO.Token;
import com.example.demo.Services.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTCreate implements JWTService<Token> {
    private final String SECRET_KEY = "ouqebfdouiebfouqewfnuoqewnhfouewnfouewnh";
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    @Override
    public String get(Token token) {
        var claims = new HashMap<String, Object>();
        
        claims.put("id", token.getId());
        claims.put("role", token.getRole());

        return get(claims);
    }

    @Override
    @SuppressWarnings({"UnnecessaryTemporaryOnConversionFromString", "CallToPrintStackTrace", "UseSpecificCatch"})
    public Token validate(String jwt) {
        try
        {
            var map = validateJwt(jwt);

            Token token = new Token();
            token.setId(Long.parseLong(map.get("id").toString()));
            token.setRole(map.get("role").toString());

            return token;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private String get(Map<String, Object> customClaims) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .claims()
                .add(customClaims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .and()
            .signWith(key)
            .compact();
    }

    private Map<String, Object> validateJwt(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(jwt)
            .getPayload();
        
        return new HashMap<>(claims);
    }
}
