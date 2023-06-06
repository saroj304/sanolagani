package com.bitflip.sanolagani.serviceimpl;

import com.bitflip.sanolagani.service.UserLogin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginImpl implements UserLogin {

	@Autowired
	UserRepo userrepo;
	@Autowired
	BCryptPasswordEncoder passwordencoder;
	private String password_result="";
	@Override
	public boolean verifyLogin(String email,String password) {
		
		Optional<User> optional = Optional.ofNullable(userrepo.findByEmail(email));
		System.out.println(optional);
		if(optional!=null) {
		List<User> user_list = userrepo.findAll();
         for(User user:user_list) {
        	 if(passwordencoder.matches(password, user.getPassword())) {
        		 password_result="success";
        		 System.out.println("success");
        	 }else {
        		 password_result="failed";
        		 System.out.println("success");
        	 }
        	 if(user.getEmail().equals(email)&&password_result.equals("success")) {
        		 return true;
        	 }
         }
		}
		return false;
	}
}
