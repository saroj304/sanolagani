package com.bitflip.sanolagani.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.service.AdminService;
import com.bitflip.sanolagani.serviceimpl.SentimentAnalysisModel;

import edu.stanford.nlp.pipeline.CoreNLPProtos.Sentiment;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	@Autowired
	AdminService adminservice;
	@Autowired
    private CompanyRepo company_repo; // Assuming you have a CompanyRepository to fetch companies
    @Autowired
    private SentimentAnalysisModel sentiment_analysis;

	@GetMapping({"/","/home"})
	public String homePage(Model model) {
		List<Company> companylist = adminservice.getAllCompany();
		Optional<List<Company>> result = Optional.ofNullable(companylist);
		if (result != null) {
		List<Company> companybasedoncapital=adminservice.listingBasedonRaisedCapital(companylist);
		model.addAttribute("companybasedoncapital", companybasedoncapital);
			return "index";
				 		 
		}			
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
	
	//Sentiment analysis based on the feedback of the company
	
	public String analyzeSentiment(@RequestParam("text") String text, Model model) {
        Sentiment sentiment = sentiment_analysis.analyzeSentiment(text);

        // Fetch a company with good sentiment from the repository
        Company company = company_repo.findBySentiment(Sentiment.);

        model.addAttribute("company", company);
        return "result";
    }
	
	
	

}
