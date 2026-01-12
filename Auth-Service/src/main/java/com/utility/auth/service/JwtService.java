package com.utility.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * Service responsible for issuing JWT tokens.
 * Uses asymmetric signing (RS256) via JwtEncoder bean.
 */
@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    // Token expiry configurable (e.g., from application.yml)
    private static final long EXPIRY_SECONDS = 3600; // 1 hour

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generate a JWT for the authenticated user.
     * @param authentication Spring Security Authentication object
     * @return signed JWT token string
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        // Collect authorities into space-separated string
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("uvision-system")                 // domain-specific issuer
                .issuedAt(now)
                .expiresAt(now.plus(EXPIRY_SECONDS, ChronoUnit.SECONDS))
                .subject(authentication.getName())        // username/email
                .claim("roles", roles)                    // align with JwtAuthenticationConverter
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}