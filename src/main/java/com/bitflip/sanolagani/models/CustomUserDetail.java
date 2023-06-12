package com.bitflip.sanolagani.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetail implements UserDetails {
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
//		get the list of roles and add each to the grantedAuthority
		Set<Role> authority = user.getRole();
		for (Role r : authority) {
//		convert the authority in the form of implementation of GrantedAuthority  i.e simple granted authority and it take the rolename as the argument 
			authoritylist.add(new SimpleGrantedAuthority(r.getName()));
		}
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
