package br.com.compassuol.pb.challenge.msauth.application.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication auth) {

        long expiration = 3600000L;

        return Jwts.builder()
                .setSubject("JWT token")
                .claim("email", auth.getName())
                .claim("roles", populateAuthorities(auth.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        String secret = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String getExpiration(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return String.valueOf(claims.getExpiration().toInstant());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Token received!");
        }
    }

    public String getEmail(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return String.valueOf(claims.get("email"));
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Token received!");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("Invalid JWT token");
        }
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> set = new HashSet<>();
        collection.forEach(authority -> set.add(authority.getAuthority()));
        return String.join(",", set);
    }

}


