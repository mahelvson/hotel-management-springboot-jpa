package com.hotel.manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hotel.manager.enums.UserType;
import com.hotel.manager.services.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						authorize -> authorize
								.requestMatchers("/", "/index.html", "/registration_visitor.html", "/hotels/**",
										"/rooms/**", "/registration", "/css/**", "/js/**", "/logout")
								.permitAll()
								.anyRequest()
								.authenticated())
				.formLogin(login -> login.loginProcessingUrl("/perform_login")
						.successHandler((request, response, authentication) -> {
							CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
							UserType userType = customUserDetails.getUserType();

							switch (userType) {
							case RECEPTIONIST:
								response.sendRedirect("/report.html");
								break;
							case MANAGER:
								response.sendRedirect("/manager.html");
								break;
							case CLIENT:
								response.sendRedirect("/index.html");
								break;
							default:
								response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
										"Tipo de usuário inválido.");
							}
						}).permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.deleteCookies("JSESSIONID")
						.permitAll())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}
