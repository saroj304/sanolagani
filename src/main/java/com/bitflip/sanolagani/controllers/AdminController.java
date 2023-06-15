package com.bitflip.sanolagani.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.models.User;
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
	
	@GetMapping("/tables/edit/{id}")
	public String deleteUnverifiedData(@PathVariable("id") String ids) {
		int id = Integer.parseInt(ids);
		admin_service.deleteData(id);
		return "redirect:/tables";
	}
	
	
	@GetMapping("/tables/edit/save/{id}")
	public String addVerifiedCompany(@PathVariable("id") String ids,Company  company,User user) {
		int id = Integer.parseInt(ids);
		admin_service.saveVerifiedCompany(id,company,user);
		return "redirect:/tables";
	}
}
