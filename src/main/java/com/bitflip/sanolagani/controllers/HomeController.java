package com.bitflip.sanolagani.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.service.AdminService;
import com.bitflip.sanolagani.serviceimpl.RecommendationInitializer;
import com.bitflip.sanolagani.serviceimpl.SentimentPreprocessor;

@Controller
public class HomeController {

	@Autowired
	AdminService adminservice;
	@Autowired
	private CompanyRepo company_repo; // Assuming you have a CompanyRepository to fetch companies
	@Autowired
	private SentimentPreprocessor pre;
	@Autowired
	RecommendationInitializer recommedationinit;
	@Autowired
	AdminController admin_controller;
	@Autowired
	UserController usercontroller;

	@GetMapping({ "/", "/home" })
	public String homePage(Model model,RedirectAttributes redirectAttributes) {
	
		User user = usercontroller.getCurrentUser();
		if(user!=null) {
		   if(user.getRole().equals("COMPANY")) {
			if(user.getCompany().getPwd_change().equals("false")) {
				redirectAttributes.addFlashAttribute("email", user.getEmail());
				return "redirect:/change_password";
			}
		}
		}
		
        List<Company> company_list = company_repo.findAll();
    	LocalDateTime now  = LocalDateTime.now();
        if(company_list.isEmpty()) {
        	return "index";
        }
        for(Company company:company_list) {
        	LocalDateTime created_date = company.getCreated();
        	String time =company.getTimespanforraisingcapital();
        	String[] timespansplit = time.split(" ",2);
        	int timespan = Integer.parseInt(timespansplit[0]);			
			
        	if(company.getStatus().equals("raising")&&now.isAfter(created_date.plusDays(timespan))){
                company.setStatus("finish");
                company_repo.save(company);
        	}
        	
        }
        
        	
		List<Company> companybasedoncapital = company_repo.findAllCompanyBasesOnRaidedCapitalDesc();
		Optional<List<Company>> result = Optional.ofNullable(companybasedoncapital);
		
		model.addAttribute("companybasedoncapital", companybasedoncapital);
		
		List<Company> companybasedondate = company_repo.findAllCompanyBasesOnCreationalDates();
		Optional<List<Company>> result1 = Optional.ofNullable(companybasedondate);
		
		List<Company> c_list = pre.getCompaniesWithGoodSentiment();
		if (result != null & result1 != null) {
			List<Company> diversifiedcompanylist = recommedationinit.getRecommendCompanies();
            System.out.println(diversifiedcompanylist);
			model.addAttribute("companybasedondate", companybasedondate);
			model.addAttribute("diversifiedcompanylist", diversifiedcompanylist);
			model.addAttribute("c_list", c_list);
			return "index";
		}
		
		
		return "index";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// HttpStatusReturningLogoutSuccessHandler hs=new
		// HttpStatusReturningLogoutSuccessHandler();
		request.getSession(false).invalidate();

		return "redirect:/home";
	}

	@GetMapping("/details")
	public String investNow() {
		return "details";
	}

//	Sentiment analysis based on the feedback of the company

	@GetMapping("/text")
	public String analysis() {
		List<Company> c_list = pre.getCompaniesWithGoodSentiment();
		System.out.println(c_list);

		return "index";
	}

}
