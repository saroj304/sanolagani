package com.bitflip.sanolagani.serviceimpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bitflip.sanolagani.models.CustomUserDetail;

import com.bitflip.sanolagani.models.User;

import com.bitflip.sanolagani.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserRepo userrepo;
    
  
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepo.findByEmail(username);
//		for(Role r:user.getRole()) {
//			System.out.println(r.getName());
//		}
		System.out.println(user.getRole());
		if (user == null) {
			throw new UsernameNotFoundException("user does not exist");
		}
		return new CustomUserDetail(user);
		   
	}
//	private Collection<? extends GrantedAuthority> getAuthorities() {
//		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//	}
	   

}