package com.example.demo.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenGenerator jwtTokenGenerator;
    @Autowired
    private final UserService userService;

    public JwtTokenFilter(JwtTokenGenerator jwtTokenGenerator, UserService userService) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //取得token
    	String token = extractTokenFromRequest(request);
        if (token != null && jwtTokenGenerator.validateToken(token)) {
        	//取得subject(在此為帳號)
            String subject = jwtTokenGenerator.getSubjectFromToken(token);
        	// 檢查使用者角色是否為 "admin"
            if (isAdminUser(subject)) {
            	//放使用者主體、憑證跟授權資料
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                		subject, 
                		null,
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
                //存儲當前使用者的身份驗證資訊的容器
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 檢查使用者角色是否為 "user"
            else if (isUserUser(subject)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                		subject, 
                		null,
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //將請求和響應傳遞給下一個過濾器或Servlet
        filterChain.doFilter(request, response);
    }

    //提取token
    private String extractTokenFromRequest(HttpServletRequest request) {
    	//請求的標頭中獲取名為"Authorization"的值
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); //去除"Bearer "字串取得JWT token
        }
        return null;
    }
    
    //是不是admin
    private boolean isAdminUser(String subject) {
        return userService.hasAdminRole(subject);
    }
    
    //是不是user
    private boolean isUserUser(String subject) {
        return userService.hasUserRole(subject);
    }
}

