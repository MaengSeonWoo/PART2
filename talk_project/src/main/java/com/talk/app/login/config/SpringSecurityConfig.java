package com.talk.app.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.talk.app.login.web.CustomAccessDeniedHandler;
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
            	// 모든 권한이 혀용된 페이지들
                .antMatchers("/", "/img/**", "/main/**",  "/docs/**", "/production/**","/calendar/**", "/chatbot/**,", "/", "/login", "/loginsel", 
                			 "/findId", "/findIdResult", "/noticeList", "/noticeInfo", "/posting/**", "/qnaList", "/qnaInfo", "/qnaInsert", "/deleteQna", 
                			 "/search", "/signsel", "/signInsert", "/checkUserId", "/cosignInsert", "/checkCoUserId", "/analyze", "/videoInfo", "/videoList","/admin/sendEmail"
                			 )
                .permitAll() 
                // 일반회원만 접근이 허용된 페이지들
                .antMatchers("/userwelfarelist", "/userMain/resume/**", "/userMain", "/userupdate", "/userdelete", "/canceluserdel", "/upload-form", "/uploadAndOcr")
                .hasAuthority("ROLE_USER") 
                // 기업회원만 접근이 허용된 페이지들
                .antMatchers("/CoUserMain", "/CoUserUpdate", "/couserdelete", "/cancelDel", "/coresumelist", "/coresumeinfo/**", 
                			 "/copostingList", "/copostingInfo", "/copostingInsert", "/copostingUpdate", "/copostingDelete")
                .hasAuthority("ROLE_CO_USER")
                // 관리자만 접근이 허용된 페이지들
                .antMatchers("/admin/**", "/noticeInsert", "/noticeUpdate", "/noticeDelete", "/replyInsert", "/deleteReply", "/videoInsert","/sendEmail","/mail")
                .hasAuthority("ROLE_ADMIN") 
                .and()
            .formLogin() 
                .loginPage("/login") 
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .defaultSuccessUrl("/") 
                .permitAll()
                .successHandler(customAuthenticationSuccessHandler()) 
                .and()
            .logout()
                .logoutSuccessUrl("/login") 
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) 
                .and()
            .csrf().disable(); 
      
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(coUserUpdateService); // 의존성 주입
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }
}
