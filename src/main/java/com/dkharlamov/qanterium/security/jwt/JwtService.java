package com.dkharlamov.qanterium.security.jwt;

import com.dkharlamov.qanterium.security.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${qanterium.security.jwt.time-to-live}")
    private int jwtTtl;
    private static final String SECRET_KEY = "397A24432646294A404E635266546A576E5A7234753778214125442A472D4B61";

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsProvider) {
        final Claims claims = extractAllClaims(token);
        return claimsProvider.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User userDetails) {
        return generateToken(generateClaims(userDetails), userDetails.getUsername());
    }

    private HashMap<String, Object> generateClaims(User user) {
        var claims = new HashMap<String, Object>();
        claims.put("Email", user.getEmail());
        claims.put("FirstName", user.getFirstname());
        claims.put("LastName", user.getLastname());
        claims.put("Role", user.getRole());
        return claims;
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            String username
    ) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTtl))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
