package com.bitflip.sanolagani.controllers;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.service.UserService;
import com.bitflip.sanolagani.serviceimpl.EmailService;


@Controller
public class RegisterController {
	@Autowired
	UserService userservice;
	@Autowired
	BCryptPasswordEncoder passwordencoder;
	@Autowired
	EmailService emailservice;
	
	
	@GetMapping("/register")
	public String registerPage(@ModelAttribute("user")User user) {
		return "signup";
	}
	
	@PostMapping("/register")
	public String saveUser(@Valid @ModelAttribute("user")User user ,BindingResult result) {
		System.out.println(result.hasErrors());
		if(result.hasErrors()) {
			return"signup";
		}
		user.setPassword(passwordencoder.encode(user.getPassword()));
		emailservice.sendEmail(user.getEmail());
		
		userservice.saveUser(user);
		return "index";
	}
}
