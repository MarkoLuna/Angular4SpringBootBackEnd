package com.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers(HttpMethod.POST, "/login")
				.permitAll().anyRequest().authenticated().and()
				// We filter the api/login requests
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				// And filter other requests to check the presence of JWT in header
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Create a default account
		// auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
		auth.userDetailsService(userService);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		List<String> METHODS = Arrays.asList("POST", "GET", "PUT", "OPTIONS", "DELETE", "PATCH");
		List<String> ORIGINS = Arrays.asList("http://127.0.0.1:4200","http://localhost:4200");
		List<String> HEADERS = Arrays.asList("Authorization", "authorization",
				"Origin", "X-Requested-With", "Content-Type", "Accept", "X-XSRF-TOKEN", "credential");

		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(ORIGINS);
		configuration.setAllowedMethods(METHODS);
		configuration.setMaxAge(3600l);
		configuration.setAllowedHeaders(HEADERS);
		configuration.setExposedHeaders(HEADERS);
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
// https://howtodoinjava.com/spring5/webmvc/spring-mvc-cors-configuration/
// https://github.com/szerhusenBC/jwt-spring-security-demo
