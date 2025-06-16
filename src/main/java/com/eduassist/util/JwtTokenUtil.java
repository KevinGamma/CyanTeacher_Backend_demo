package com.eduassist.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成 JWT 令牌
     * @param username 用户名
     * @return JWT 令牌字符串
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000); // expiration 是秒，转换为毫秒

        return Jwts.builder()
                .setSubject(username)        // 设置用户名
                .setIssuedAt(now)           // 设置签发时间
                .setExpiration(expiryDate)   // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret) // 使用 HS512 签名算法和密钥
                .compact();
    }

    /**
     * 从 JWT 令牌中提取用户名
     * @param token JWT 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * 验证 JWT 令牌
     * @param token JWT 令牌
     * @param userDetails 用户详情
     * @return 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 检查 JWT 令牌是否过期
     * @param token JWT 令牌
     * @return 是否过期
     */
    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }
}
