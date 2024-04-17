package com.authentication.config.jwt;

import ch.qos.logback.classic.Logger;
import com.authentication.entity.User;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Artur Tomeyan
 * @date 02/11/2022
 */
@Component
public class JwtProvider {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.refresh-token.expiration-time}")
    private Long accessTokenExpirationTime;
    @Value("${jwt.refresh-token.expiration-time}")
    private Long refreshTokenExpirationTime;
    @Value("${jwt.secret-key}")
    private String secretKey;
    private String encodedKey;

    @PostConstruct
    protected void init() {
        this.encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String accessTokenGenerator(User user) {
        return generateToken(user, accessTokenExpirationTime);
    }

    public String refreshTokenGenerator(User user) {
        return generateToken(user, refreshTokenExpirationTime);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(encodedKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Token expired.");
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token.");
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature.");
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported.");
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty.");
        }

        return false;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(encodedKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(User user, Long expiration) {

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("email", user.getEmail());
        Date date = new Date();
        Date validate = new Date(date.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS512, encodedKey)
                .compact();
    }
}