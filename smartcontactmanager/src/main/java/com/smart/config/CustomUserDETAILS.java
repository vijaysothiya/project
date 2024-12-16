package com.smart.config;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart.entities.User;

public class CustomUserDETAILS implements UserDetails {

	private User user;
	
	
	
	
	
	public CustomUserDETAILS(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
	SimpleGrantedAuthority authority=	new SimpleGrantedAuthority(user.getRole());
	
		
		return List.of(authority);
	}

	@Override
	public String getPassword() {


		return user.getPassword();
	}

	@Override
	public String getUsername() {


		return user.getEmail();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

}
