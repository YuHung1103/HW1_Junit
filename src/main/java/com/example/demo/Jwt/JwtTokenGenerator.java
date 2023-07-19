package com.example.demo.Jwt;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator {

    private final String secretKey;
    private final long expirationTime;

    public JwtTokenGenerator(@Value("${jwt.secretKey}") String secretKey, 
    		@Value("${jwt.expirationTime}") long expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    //生成Jwt token
    public String generateToken(String userAccount) {
        Date now = new Date();
        //到期時間
        Date expiration = new Date(now.getTime() + expirationTime);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userAccount)
                //簽發時間
                .setIssuedAt(now)
                //到期時間
                .setExpiration(expiration)
                //指定簽名算法和金鑰
                .signWith(SignatureAlgorithm.HS256, secretKey);
        //壓縮
        return jwtBuilder.compact();
    }

    //token是不是有效
    public boolean validateToken(String token) {
        try {
        	//解析token
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubjectFromToken(String token) {
    	//用來取得token中的payload
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        //取得payload中的subject
        return claims.getSubject();
    }
}

