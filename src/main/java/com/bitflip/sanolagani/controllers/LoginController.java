package com.bitflip.sanolagani.controllers;




import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginController {
	
@GetMapping("/login")
	public String loginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null|| authentication.getName().equals("anonymousUser")) {
			return "user_login";
		}else {
			return "redirect:/home";
		}
	}
}	




