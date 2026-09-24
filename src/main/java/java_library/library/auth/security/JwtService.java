
package java_library.library.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import java_library.library.common.enums.JwtTokenType;
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
                accessExpiration,
                JwtTokenType.ACCESS
        );
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(
                userId,
                null,
                null,
                refreshExpiration,
                JwtTokenType.REFRESH
        );
    }

    private String generateToken(
            Long userId,
            String username,
            java.util.List<String> roles,
            long expiration,
            JwtTokenType tokenType
    ) {
        Date now = new Date();

        Date expirationDate = new Date(
                now.getTime() + expiration
        );

        var builder = Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expirationDate).claim("type", tokenType.name());

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

    public JwtTokenType extractTokenType(String token) {
        String type = extractAllClaims(token).get("type", String.class);
        return JwtTokenType.valueOf(type);
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

    public boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public boolean isAccessToken(String token) {
        try {
            return extractTokenType(token) == JwtTokenType.ACCESS;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            return extractTokenType(token) == JwtTokenType.REFRESH;
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

