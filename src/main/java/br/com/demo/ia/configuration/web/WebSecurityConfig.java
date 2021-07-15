package br.com.demo.ia.configuration.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.demo.ia.configuration.CommonBeansConfig;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	
	private final CommonBeansConfig commonBeansComponent;
	
	private final String realm;
	
	public WebSecurityConfig(
			final CommonBeansConfig commonBeansComponent,
			final UserDetailsService userDetailsService,
			
			@Value("${demo_ia_back.realm}")
			final String realm	
	) {
		this.realm = realm;
		this.userDetailsService = userDetailsService;
		this.commonBeansComponent = commonBeansComponent;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf()
				.disable()
			.authorizeRequests()
			.anyRequest()
				.authenticated()
			.and()
			.httpBasic()
				.realmName(realm);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(commonBeansComponent.passwordEncoder());
	}
	
}
