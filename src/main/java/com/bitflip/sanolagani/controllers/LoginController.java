package com.bitflip.sanolagani.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
=======
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
=======
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9

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
<<<<<<< HEAD
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
=======
@PostMapping("/login")
public String loginpage() {
	return "login";
}
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9
}
