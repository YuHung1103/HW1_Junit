package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.Jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
		//HTTP請求規則
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/v3/api-docs/**","/swagger-ui/**").permitAll()
				.requestMatchers("/redis").permitAll()
                .requestMatchers("/kid/**").permitAll()
                .requestMatchers("/father/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/register").permitAll()
                .requestMatchers(HttpMethod.PUT,"/forget").permitAll()
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .requestMatchers(HttpMethod.POST,"user/logout").authenticated()
                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/book/**").hasRole("ADMIN")
                .anyRequest().permitAll());
		
		//加入filter，先執行jwtfilter
//		.addFilterBefore(jwtTokenFilter(), BasicAuthenticationFilter.class);
		return http.build();
    }
	
	//建立一個JwtTokenFilter的方法
	@Bean
    public JwtTokenFilter jwtTokenFilter() throws Exception {
        return new JwtTokenFilter();
    }

	//該AuthenticationManager不會對用戶進行身份驗證，而是取消了認證過程
	@Bean
    public AuthenticationManager noopAuthenticationManager() {
        return authentication -> {
            throw new AuthenticationServiceException("Authentication is disabled");
        };
    }
}
