package com.bitflip.sanolagani.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitflip.sanolagani.service.AdminService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	@Autowired
	AdminService adminservice;

	@GetMapping({"/","/home"})
	public String homePage(Model model) {
		
		return "index";
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request){
			//HttpStatusReturningLogoutSuccessHandler hs=new HttpStatusReturningLogoutSuccessHandler();
			request.getSession(false).invalidate();

			return "redirect:/home";
		}
	@GetMapping("/details")
	public String investNow() {
		return "details";
	}

}
