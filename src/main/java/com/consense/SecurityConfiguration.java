package com.consense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
	      .withUser("user").password("test").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//	      .httpBasic().and()
//	      .authorizeRequests()
//	      	.antMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN");
//	    http.csrf().disable();
		http
        .authorizeRequests()
        	.antMatchers("/greeting/**", "/login").permitAll()    
            .anyRequest().authenticated()
            .and()
        .formLogin()
	        .loginPage("/login") 
	        .permitAll();
	}
	
//	private UserDetailsService userDetailsService() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
	
}
