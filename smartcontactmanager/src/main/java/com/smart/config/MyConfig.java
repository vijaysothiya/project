package com.smart.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.*;



@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfiguration{
	
	
	@Bean
	@Lazy
	public UserDetailsService getUserDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	
	@Bean
	@Lazy
	public BCryptPasswordEncoder passwordEncoder()
{
		return new BCryptPasswordEncoder();
   	}
	
	@Bean
	@Lazy
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
	
		return daoAuthenticationProvider;
	}
	
	///configur methods.....
	
	@Bean
	@Lazy
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
	
//	          http.csrf(csrf->csrf.disable()).authorizeHttpRequests(auth->auth.requestMatchers("/**").permitAll()
//	        		  .anyRequest().authenticated()).formLogin(form->form.loginPage("/login/**").permitAll())
//	          .logout(logout->logout.permitAll());
	          
	          http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/user/**").hasRole("USER").requestMatchers("/**").permitAll().and()
          .formLogin().loginPage("/signin")
          .loginProcessingUrl("/dologin")
          .defaultSuccessUrl("/user/index")
          .and().csrf().disable();
         return http.build();
		
	}
	
	
	
	
	
	
	
	
	
	

}
