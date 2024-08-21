package com.talk.app.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.talk.app.login.web.CustomAuthenticationSuccessHandler;
import com.talk.app.mypage.service.CoUserUpdateService;

@Configuration
@EnableWebSecurity
@EnableScheduling
public class SpringSecurityConfig {

    private final CoUserUpdateService coUserUpdateService;

    // 생성자 주입
    public SpringSecurityConfig(CoUserUpdateService coUserUpdateService) {
        this.coUserUpdateService = coUserUpdateService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/img/**", "/signInsert", "/checkUserId", "/cosignInsert", "/checkCoUserId","/login", "/signsel","/cologin","/main/**",  "/docs/**", "/production/**","/admin/**").permitAll() 
//                .antMatchers("/admin").hasRole("ADMIN") 
                .antMatchers("/posting").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") 
//                .anyRequest().authenticated()
                .antMatchers("/", "/img/**", "/main/**",  "/docs/**", "/production/**",
                			"/signInsert", "/cosignInsert", "/checkUserId", "/checkCoUserId","/login", "/signsel","/cologin", "/findId","/findIdResult",
                			"/posting/**")
                .permitAll() 
//                .antMatchers("/admin")
//                .hasRole("ADMIN") 
                .antMatchers("/posting")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") 
//                .anyRequest().authenticated()
                .antMatchers("/admin")
                .hasRole("ADMIN") 
                .antMatchers("/mypage/**")
                .hasAnyRole("USER", "ADMIN", "CO_USER") 
//                .anyRequest().authenticated()
                .and()
            .formLogin() 
                .loginPage("/login") 
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .defaultSuccessUrl("/") 
                .permitAll()
                .successHandler(customAuthenticationSuccessHandler()) // 성공 핸들러 설정
                .and()
            .logout()
                .logoutSuccessUrl("/login") 
                .permitAll()
                .and()
            .csrf().disable(); 
      
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(coUserUpdateService); // 의존성 주입
    }
}
