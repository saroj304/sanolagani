package com.bitflip.sanolagani.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.service.AdminService;
import com.bitflip.sanolagani.serviceimpl.SentimentPreprocessor;

@Controller
public class HomeController {

	@Autowired
	AdminService adminservice;
	@Autowired
	private CompanyRepo company_repo; // Assuming you have a CompanyRepository to fetch companies
	@Autowired
	private SentimentPreprocessor pre;

	@GetMapping({ "/", "/home" })
	public String homePage(Model model) {
		List<Company> companylist = adminservice.getAllCompany();
		Optional<List<Company>> result = Optional.ofNullable(companylist);
		if (result != null) {
			for (Company cmp : companylist)

			{
				System.out.println("iam inside homecontroller in /home url");
				System.out.println(cmp.getCreated());
				System.out.println(cmp.getCompanyname());
				System.out.println("fuck you" + cmp.getImage());
			}
			List<Company> companybasedoncapital = adminservice.listingBasedonRaisedCapital(companylist);

			for (Company cmp : companybasedoncapital) {
				System.out.println(cmp);
				System.out.println("iam inside homecontroller in /home url ");
				System.out.println(cmp.getCompanyname());
				System.out.println(cmp.getPreviouslyraisedcapital());
			}
			List<Company> companybasedonDate = adminservice.listingBasedonRecentDate(companylist);

			model.addAttribute("companybasedondate", companybasedonDate);
			model.addAllAttributes(companybasedoncapital);
			model.addAttribute("companybasedoncapital", companybasedoncapital);
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

		for (Company com : c_list) {
			System.out.println(com.getCompanyname());
		}

		return "index";
	}

}
