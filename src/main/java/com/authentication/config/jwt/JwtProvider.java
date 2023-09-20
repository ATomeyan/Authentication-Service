package com.authentication.config.jwt;

import com.authentication.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * @author Artur Tomeyan
 * @date 02/11/2022
 */
@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh-token.expiration-time}")
    private Long refreshTokenExpirationTime;

    public String accessTokenGenerator(User user) {
        return generateToken(user, accessTokenExpirationTime);
    }

    public String refreshTokenGenerator(User user) {
        return generateToken(user, refreshTokenExpirationTime);
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return true;

        } catch (ExpiredJwtException e) {
            log.error("Token expired.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
        } catch (SignatureException e) {
            log.error("Invalid JWT signature.");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.");
        }

        return false;
    }

    private String generateToken(User user, Long expiration) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());
        claims.put("authorities", authentication.getAuthorities());
        Date date = new Date();
        Date validate = new Date(date.getTime() + expiration);

        Key key = new SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS512.getJcaName());

        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}