package com.bitflip.sanolagani.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Company;

@Service
public class CustomCompanyDetail implements UserDetails{
	
	@Autowired
	Company company;
	
    public CustomCompanyDetail() {
		
	} 


	public CustomCompanyDetail(Company company) {
		this.company = company;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritylist = new ArrayList<>();
		String role = company.getRole();
		authoritylist.add(new SimpleGrantedAuthority(role));
			return authoritylist;
	}

	@Override
	public String getPassword() {
		return company.getPassword();
	}

	@Override
	public String getUsername() {
		
		return company.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
