package dev.mosaleh.Library.Management.System.service;

import dev.mosaleh.Library.Management.System.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.nio.file.Paths;
import java.io.IOException;

@Service
public class JWTService {

    private String secretKey;

    public JWTService() {
        this.secretKey = getSecretKey();
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public String generateToken(
            UserDetails userDetails
    ) {
        Map<String, Object> claims = new HashMap<>();
        if(userDetails instanceof User) {
            User user = (User) userDetails;
            claims.put("userId", user.getId());
            claims.put("roles", user.getAuthorities());
            claims.put("isPremium", user.getIsPremium());
        }return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private <T> T extractClaim  (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 605800000))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private String getSecretKey(){
        try {
            secretKey = new String(Files.readAllBytes(Paths.get("src/main/resources/secretKey.txt")));
            System.out.println("\nSecret Key successfully loaded\n");
        }catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("failed to load secret key");

            System.out.println("\n\n\n\n\n\n\nYou should provide a file named secretKey in resources and add your secret key to it\n\n\n\n\n\n\n");
        }
        return secretKey;
    }

    private Key getSigninKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
