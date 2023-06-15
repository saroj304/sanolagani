package com.bitflip.sanolagani.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.User;



@Service
public class CustomUserDetail implements UserDetails {
	String ROLE_PREFIX = "ROLE_";
	@Autowired
	User user;

	CustomUserDetail() {

	}

	public CustomUserDetail(User u) {
		this.user = u;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritylist = new ArrayList<>();
		String role = ROLE_PREFIX+user.getRole();
		authoritylist.add(new SimpleGrantedAuthority(role));
		return authoritylist;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
