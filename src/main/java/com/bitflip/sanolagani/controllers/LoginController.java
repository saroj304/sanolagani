package com.bitflip.sanolagani.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitflip.sanolagani.service.UserLogin;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {
	@Autowired
	UserLogin userlogin;
	
@GetMapping("/login")
	public String loginPage() {
		return "user_login";
	}
@PostMapping("/login")
public String loginpage(@RequestParam("email") String email,@RequestParam("password") String password) {
	boolean bool=userlogin.verifyLogin(email,password);
	if(bool) {
		System.out.println(bool);
		return "index";
	}
	return "user_login";
}
	}



