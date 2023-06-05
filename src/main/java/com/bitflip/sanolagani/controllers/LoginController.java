package com.bitflip.sanolagani.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
=======
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb

@Controller
public class LoginController {
@GetMapping("/login")
	public String loginPage() {
<<<<<<< HEAD
		return "home";
	}
@PostMapping("/login")
public String loginpage() {
	return "login";
}
=======
		return "login";
	}
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
}
