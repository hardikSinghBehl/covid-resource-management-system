package com.hardik.pomfrey.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hardik.pomfrey.security.CustomUserDetailService;
import com.hardik.pomfrey.security.filter.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailService customUserDetialService;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetialService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/user/**", "/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
						"/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/**",
						"/swagger-ui/**", "/v3/api-docs/**", "/configuration/**", "/swagger*/**")
				.permitAll().anyRequest().authenticated().and()
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}