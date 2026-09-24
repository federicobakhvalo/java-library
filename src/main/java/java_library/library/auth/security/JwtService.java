
package java_library.library.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import java_library.library.auth.dto.response.JwtUserData;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration}") long accessExpiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(JwtUserData userData) {
        return generateToken(
                userData.getUserId(),
                userData.getUsername(),
                userData.getRoles(),
                accessExpiration
        );
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(
                userId,
                null,
                null,
                refreshExpiration
        );
    }

    private String generateToken(
            Long userId,
            String username,
            java.util.List<String> roles,
            long expiration
    ) {
        Date now = new Date();

        Date expirationDate = new Date(
                now.getTime() + expiration
        );

        var builder = Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expirationDate);

        if (username != null) {
            builder.claim("username", username);
        }

        if (roles != null) {
            builder.claim("roles", roles);
        }

        return builder
                .signWith(secretKey)
                .compact();
    }

    public String extractUserId(String token) {
        return extractAllClaims(token)
                .getSubject();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token)
                .get("username", String.class);
    }

    @SuppressWarnings("unchecked")
    public java.util.List<String> extractRoles(String token) {
        return extractAllClaims(token)
                .get("roles", java.util.List.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

