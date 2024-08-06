package com.talk.app.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/img/**", "/signInsert","/login", "/cologin","/main/**",  "/docs/**", "/production/**").permitAll() // 회원, 비회원, 관리자
                .antMatchers("/admin").hasRole("ADMIN") // 관리자
                .antMatchers("/posting").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // 회원
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login") // 로그인 페이지 설정
                .usernameParameter("userId")
                .passwordParameter("userPw")
//                .usernameParameter("CoUserId")
//                .passwordParameter("CoUserPw")
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉션 설정
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/login") // 로그아웃 후 리다이렉션 설정
                .permitAll()
                .and()
            .csrf().disable(); // CSRF 보호 비활성화
        
        return http.build();
    }
}