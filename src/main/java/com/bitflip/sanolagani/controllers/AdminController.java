package com.bitflip.sanolagani.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.service.AdminService;



@Controller
public class AdminController {
	@Autowired
	AdminService admin_service;

	@GetMapping("/tables")
	public String getunverifiedcompany(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null|| authentication.getName().equals("anonymousUser")) {
			List<UnverifiedCompanyDetails> details = admin_service.fetchAll();
			if(details!=null) {
					model.addAttribute("UnverfiedCompanies",details);
					return "table";	
			}
			return "table";
		}else {
			return "redirect:/home";
		}

	}
}
