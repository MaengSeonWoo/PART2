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
		SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // HttpSecurity ���� ���� ���
			http.authorizeHttpRequests()
				.antMatchers("/**","/signInsert") // ������
					.permitAll()
				.antMatchers("/admin")		// ���
					.hasRole("ADMIN")
				.antMatchers("/signInsert")
					.hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.anyRequest()
					.authenticated()
			.and()
			.formLogin()
				.defaultSuccessUrl("/admin") // �α��������� � �������� ���� ������
			.and()
			.logout()
			.logoutSuccessUrl("/login"); // �α׾ƿ������� � �������� ���� ������;
					
			http.csrf().disable();
			
			return http.build();
		}
}
