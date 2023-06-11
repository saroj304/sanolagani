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
    System.out.println(authentication.getName());
	if (authentication == null|| authentication.getName().equals("anonymousUser")) {
		return "user_login";
	}else {
		return "redirect:/home";
	}
}
}
//@PostMapping("/login")
//public String loginpage(@RequestParam("email") String email,@RequestParam("password") String password) {
//	boolean bool=userlogin.verifyLogin(email,password);
//	if(bool) {
//		System.out.println(bool);
//		return "index";
//	}
//	return "user_login";
//}

	




