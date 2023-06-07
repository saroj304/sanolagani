package com.bitflip.sanolagani.service;




import com.bitflip.sanolagani.models.User;

public interface UserLogin {
 public User verifyLogin(String email,String password);
}
