package com.bitflip.sanolagani.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/myDashboard")
	public String myDashboard() {
		return "dashboard";
	}
	@GetMapping("/myDashboard1")
	public String myDashboard1() {
		return "dashboard1";
	}
}
