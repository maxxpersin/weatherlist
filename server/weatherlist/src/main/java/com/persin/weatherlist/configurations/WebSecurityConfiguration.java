package com.persin.weatherlist.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.persin.weatherlist.security.LoginFailureHandler;
import com.persin.weatherlist.security.LoginSuccessHandler;
import com.persin.weatherlist.security.LogoutSuccessHandlerImpl;
import com.persin.weatherlist.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userDetailsService;
	
	@Autowired
	LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	LoginFailureHandler loginFailureHandler;
	
	@Autowired
	private LogoutSuccessHandlerImpl logoutSuccessHandler;
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return this.userDetailsService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configAuthBuilder(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.userDetailsService(userDetailsService);
	}

	private CsrfTokenRepository csrfTokenRepository() {
		CookieCsrfTokenRepository rep = new CookieCsrfTokenRepository();
		rep.setCookieHttpOnly(false);
		return rep;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
			.csrf()
			    .csrfTokenRepository(csrfTokenRepository())
			    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/api/v1/**"))
			    .and()
			.formLogin()
				.loginProcessingUrl("/api/v1/login")
				.successHandler(loginSuccessHandler)
				.failureHandler(loginFailureHandler)
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/api/v1/logout")
				.invalidateHttpSession(true)
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
			.authorizeRequests()
				.antMatchers("/**", "/assets/**").permitAll()
				.antMatchers("/api/v1/**").hasRole("USER")
			.anyRequest()
				.authenticated()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	
				
	}
}
