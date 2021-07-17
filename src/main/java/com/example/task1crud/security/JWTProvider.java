package com.example.task1crud.security;

import com.example.task1crud.entity.Rollar;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTProvider {
    private static final long expireDate = 1000 * 60 * 60; //1min
    private static final String secretK = "qwertyuiop"; //1min

    public String generateToken(String username, Rollar roll) {
        Date expireToken = new Date(System.currentTimeMillis() + expireDate);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireToken)
                .claim("roles", roll)
                .signWith(SignatureAlgorithm.HS512, secretK)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        final String subject = Jwts
                .parser()
                .setSigningKey(secretK)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return subject;
    }


}
