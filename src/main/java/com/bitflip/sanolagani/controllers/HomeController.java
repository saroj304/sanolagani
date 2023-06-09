package com.bitflip.sanolagani.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitflip.sanolagani.serviceimpl.RecommendationInitializer;

@Controller
public class HomeController {
	@Autowired
    RecommendationInitializer recommedationinit;

	@GetMapping({"/","/home"})
	public String homePage() {
		 recommedationinit.getAuthenticUser();
		return "index";
	}
}
