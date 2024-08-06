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
                .antMatchers("/**", "/signInsert","/login", "/notice/noticeList").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/signInsert").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login") // 로그인 페이지 설정
                .defaultSuccessUrl("/admin") // 로그인 성공 후 리다이렉션 설정
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
