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

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailService implements UserDetailsService {
	private final Map<String, CustomUserDetail> userRegistry = new HashMap<>();
	@Autowired
	UserRepo userrepo;
	@Autowired
	CompanyRepo company_repo;
	private Company company;
	private User user;
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		this.user = userrepo.findByEmail(username);
		 this.company = company_repo.findByEmail(username);
		if (user != null) {
			return new CustomUserDetail(user);
		}else if(company != null)
			return new CustomCompanyDetail(company);
		else {
			throw new UsernameNotFoundException("user does not exist");

		}
	  
	   
	}

}