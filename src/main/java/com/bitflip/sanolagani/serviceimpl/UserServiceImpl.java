package com.bitflip.sanolagani.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.UserRepo;
import com.bitflip.sanolagani.service.AdminService;
import com.bitflip.sanolagani.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userrepo;
	
	@Override
	public void saveUser(User u) {
		userrepo.save(u);
	}

	@Override
	public boolean checkEmail(String email) {
		User user = userrepo.findByEmail(email);
		if(user==null) {
			return false;
		}
		return true;
	}

	@Override
	public void updatePassword(String email, String password) {
		List<User> userlist = userrepo.findAll();
		for(User user:userlist) {
			if(user.getEmail().equals(email)) {
				String encode_password=AdminServiceImpl.encodePassword(password);
				user.setPassword(encode_password);
				userrepo.save(user);
			}
		}
		
	}

}
