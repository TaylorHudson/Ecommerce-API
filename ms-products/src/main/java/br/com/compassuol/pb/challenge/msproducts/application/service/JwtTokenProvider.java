package br.com.compassuol.pb.challenge.msproducts.application.service;

import br.com.compassuol.pb.challenge.msproducts.framework.exception.InvalidTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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

    public String getExpiration(String token) throws InvalidTokenException {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return String.valueOf(claims.getExpiration().toInstant());
        } catch (Exception e) {
            throw new InvalidTokenException("The passed token is expired");
        }
    }

    public String getEmail(String token) throws InvalidTokenException {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return String.valueOf(claims.get("email"));
        } catch (Exception e) {
            throw new InvalidTokenException("The passed token is expired");
        }
    }

    public boolean validateToken(String token) throws InvalidTokenException {
        try {
        Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parse(token);
        return true;
        } catch (Exception ex) {
            throw new InvalidTokenException("The passed token is invalid");
        }
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> set = new HashSet<>();
        collection.forEach(authority -> set.add(authority.getAuthority()));
        return String.join(",", set);
    }

}


