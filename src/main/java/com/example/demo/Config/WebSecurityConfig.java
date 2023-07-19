package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.Dao.BlacklistRepository;
import com.example.demo.Jwt.JwtTokenFilter;
import com.example.demo.Jwt.JwtTokenGenerator;
import com.example.demo.Service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final JwtTokenGenerator jwtTokenGenerator;
	private final UserService userService;
	private final BlacklistRepository blacklistRepository;
    
    public WebSecurityConfig(JwtTokenGenerator jwtTokenGenerator, UserService userService, BlacklistRepository blacklistRepository) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.userService = userService;
        this.blacklistRepository = blacklistRepository;
    }
    
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		//HTTP請求規則
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/v3/api-docs/**","/swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.POST,"/register").permitAll()
                .requestMatchers(HttpMethod.PUT,"/forget").permitAll()
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .requestMatchers(HttpMethod.POST,"user/logout").authenticated()
                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/book/**").hasRole("ADMIN")
                .anyRequest().authenticated())
		//加入filter
		.addFilterBefore(jwtTokenFilter(), BasicAuthenticationFilter.class);
		return http.build();
    }
	
	//建立一個JwtTokenFilter的方法
	@Bean
    public JwtTokenFilter jwtTokenFilter() throws Exception {
        return new JwtTokenFilter(jwtTokenGenerator, userService, blacklistRepository);
    }
	
	//不知道用法，也不知道為什麼加入這兩個方法後，就不會自動生成安全碼
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String account = authentication.getPrincipal().toString();
                String password = authentication.getCredentials().toString();
//                boolean authenticated = authenticateUser(account, password);
//                if (authenticated) {
                    return new UsernamePasswordAuthenticationToken(account, password);
//                } else {
//                    throw new IllegalArgumentException("Authentication failed");
//                }
            }
        };
    }

//    private boolean authenticateUser(String account, String password) {
//        return account.equals("admin") && password.equals("password");
//    }
}
