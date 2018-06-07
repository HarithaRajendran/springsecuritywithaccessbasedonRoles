package com.asahi.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class ConfigForSpringSecurity extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/").hasRole("EMPLOYEE")
			.antMatchers("/leader/**").hasRole("MANAGER")
			.antMatchers("/system/**").hasRole("ADMIN")
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.permitAll()
					.and()
						.logout().permitAll()
						.and()
							.exceptionHandling().accessDeniedPage("/access-denied");
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
				.withUser(users.username("Hari").password("Hari").roles("EMPLOYEE"))
				.withUser(users.username("Haritha").password("Haritha").roles("EMPLOYEE","ADMIN"))
				.withUser(users.username("Archana").password("Archana").roles("EMPLOYEE","MANAGER"));
	}

	
}
