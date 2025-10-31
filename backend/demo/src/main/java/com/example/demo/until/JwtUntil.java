package com.example.demo.until;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUntil {

    // Tạo key bảo mật
    private static final String SECRET_KEY = "my_super_secret_key_for_jwt_token_generation_123456"; // >= 32 ký tự
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Tạo token client
    public String generateToken(String account, Long accountId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountid", accountId);
        claims.put("account", account);
        claims.put("name", username);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    // Tạo token client
    public String generateTokenGoogle(String account, Long accountId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountid", accountId);
        claims.put("account", account);
        claims.put("name", username);
        claims.put("provider", "Google");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
 // Tạo token
    public String generateTokenAdmin(String account, Long accountId, String username, boolean canAdd, boolean canEdit, boolean canDelete) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountid", accountId);
        claims.put("account", account);
        claims.put("name", username);
        claims.put("add", canAdd);
        claims.put("edit", canEdit);
        claims.put("delete", canDelete);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(account)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    // Lấy username từ token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Kiểm tra hết hạn
    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Kiểm tra token hợp lệ
    public Boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public boolean isValidTokenFormat(String token) {
        // Kiểm tra rỗng hoặc null
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        

        // JWT hợp lệ phải có đúng 2 dấu chấm (3 phần: header.payload.signature)
        long dotCount = token.chars().filter(ch -> ch == '.').count();
        return dotCount == 2;
    }

    public boolean validateToken(String token) {
        if (!isValidTokenFormat(token)) {
            return false;
        }
        try {
            // Nếu định dạng hợp lệ thì kiểm tra chữ ký JWT
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true; // Token hợp lệ
        } catch (Exception e) {
            return false;
        }
    }
}
