package com.bitflip.sanolagani.service;

import org.springframework.stereotype.Service;

public interface UserLogin {
 public boolean verifyLogin(String email,String password);
}
