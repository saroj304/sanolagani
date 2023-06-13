package com.bitflip.sanolagani.serviceimpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.serviceimpl.CustomUserDetail;

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserRepo userrepo;
	@Autowired
	CompanyRepo company_repo;
    
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepo.findByEmail(username);
		Company company = company_repo.findByEmail(username);
		if (user != null) {
			return new CustomUserDetail(user);
		}else if(company != null)
			return new CustomCompanyDetail(company);
		else {
			throw new UsernameNotFoundException("user does not exist");

		}
	  
	   
	}
}