package com.bitflip.sanolagani.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitflip.sanolagani.models.Company;
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

	@GetMapping({ "/", "/home" })
	public String homePage(Model model) {
		List<Company> companylist = adminservice.getAllCompany();
		Optional<List<Company>> result = Optional.ofNullable(companylist);
		if (result != null) {
		//	List<Company> companybasedoncapital = adminservice.listingBasedonRaisedCapital(companylist);
			//model.addAttribute("companybasedoncapital", companybasedoncapital);
			
			
			List<Company> companybasedondate = adminservice.listingBasedonRecentDate(companylist);
			model.addAttribute("companybasedondate", companybasedondate);
			
			List<Company> diversifiedcompanylist = recommedationinit.getRecommendCompanies();
			model.addAttribute("diversifiedcompanylist", diversifiedcompanylist);
			
			
			List<Company> c_list = pre.getCompaniesWithGoodSentiment();
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

		return "index";
	}

}
