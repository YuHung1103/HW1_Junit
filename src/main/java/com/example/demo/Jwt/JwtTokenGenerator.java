package com.example.demo.Jwt;


import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
    private final String REDIS_KEY_PREFIX = "jwt:";
    private final String secretKey;
	Date now = new Date();
    
	public JwtTokenGenerator(@Value("${jwt.secretKey}") String secretKey) {
		this.secretKey = secretKey;
	}

    //生成JWT Token並存儲到Redis中
    public String generateJwtToken(String userAccount) {
        String jwtToken = Jwts.builder()
                .setSubject(userAccount)
                //簽發時間
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        //將JWT Token存儲到Redis，設置過期時間
        String redisKey = REDIS_KEY_PREFIX + userAccount;
        redisTemplate.opsForValue().set(redisKey, jwtToken, 30, TimeUnit.MINUTES); //30分鐘過期
        return jwtToken;
    }
    
	 //token是不是有效
	 public boolean validateToken(String token) {
		//先拿到redis中的key
	    String redisKey = REDIS_KEY_PREFIX + getSubjectFromToken(token);
	    //再用key來找到token
	    String storedToken = redisTemplate.opsForValue().get(redisKey);
	    return token.equals(storedToken);
	 }
	
	 public String getSubjectFromToken(String token) {
	  	 //用來取得token中的payload
	     Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	     //取得payload中的subject
	     return claims.getSubject();
	 }
	 
	 public void deleteToken(String userAccount) {
		//先拿到redis中的key
		String redisKey = REDIS_KEY_PREFIX + userAccount;
		redisTemplate.delete(redisKey);
	 }
	 
	 public boolean keyExists(String userAccount) {
		 String redisKey = REDIS_KEY_PREFIX + userAccount;
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        return valueOps.get(redisKey) != null;
    }
}

