package com.jcarmona.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcarmona.config.exception.InvalidAuthorizationHeaderException;
import com.jcarmona.model.User;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class JwtToken {

    private final SecretKey secretKey;
    private final int expirationHours;

    public JwtToken(@Value("${jwt.secretKey}") String secretKeyStr,
            @Value("${jwt.expirationHours}") int expirationHours){
    	this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes());
        this.expirationHours = expirationHours;
    }

    public String generateJwtToken(User user, SecretKey secretKey) {
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(expirationHours);
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("name",user.getName())
                .claim("email",user.getEmail())
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public Claims validateAndExtractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new InvalidAuthorizationHeaderException("Token inválido o expirado");
        }
    }

    public String extractTokenFromAuthorizationHeader(String authorizationHeader) {
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            if (authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.substring(7);
            }
        }
        throw new InvalidAuthorizationHeaderException("Encabezado de autorización inválido");
    }

    public SecretKey getSecretKey(){
        return this.secretKey;
    }


}
