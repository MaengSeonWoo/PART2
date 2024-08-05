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
		PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		

		@Bean
		SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // HttpSecurity 보안 정보 담당
			http.authorizeHttpRequests()
				.antMatchers("/**","/signInsert") // 별빼기
					.permitAll()
				.antMatchers("/admin")		// 경로
					.hasRole("ADMIN")
				.antMatchers("/signInsert")
					.hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.anyRequest()
					.authenticated()
			.and()
			.formLogin()
				.defaultSuccessUrl("/admin") // 로그인했을때 어떤 페이지로 가고 싶은지
			.and()
			.logout()
			.logoutSuccessUrl("/login"); // 로그아웃했을때 어떤 페이지로 가고 싶은지;
					
			http.csrf().disable();
			
			return http.build();
		}
}
