package com.bitflip.sanolagani.service;

import com.bitflip.sanolagani.models.User;

public interface UserService {
    public void saveUser(User u);
	public boolean checkEmail(String email);
	public void updatePassword(String email, String password);
}