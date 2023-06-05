package com.bitflip.sanolagani.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
@GetMapping("/login")
	public String loginPage() {
		return "home";
	}
@PostMapping("/login")
public String loginpage() {
	return "login";
}
}
