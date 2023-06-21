package com.bitflip.sanolagani.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.InvestmentRepo;
import com.bitflip.sanolagani.repository.UserRepo;

import java.util.List;
import java.util.Set;
import java.util.HashSet;


import java.util.Map;
import java.util.HashMap;

import java.util.stream.Collectors;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class UserController {
@Autowired
UserRepo userrepo;
@Autowired
InvestmentRepo invest_repo;
@Autowired
CompanyRepo company_repo;
	
	@GetMapping("/dashboard")
	public String myDashboard(Model model) {
		User user = getCurrentUser();
		List<Investment> investment_list = user.getInvestments();
		if(investment_list!=null) {
		List<Object> totalsharelist = new ArrayList<>();
		List<String> companynamelist = new ArrayList<>();
		List<Object> totalamountlist = new ArrayList<>();
		List<Object[]> investmentlist = invest_repo.getTotalSharesPerCompany(user.getId());
	    for(Object[] obj:investmentlist) {
	    	String companyname = company_repo.getCompanyName(obj[0]);
	    	companynamelist.add(companyname);
	    	totalsharelist.add(obj[1]);
	    	totalamountlist.add(obj[2]);
	    	System.out.println(obj[2]);
		}
		
		double totalInvestment = investment_list.stream()
	            .mapToDouble(Investment::getAmount)
	            .sum();
         int size = investmentlist.size();
         int totalshare = investment_list.stream()
        		     .mapToInt(Investment::getQuantity).sum();
         
  		model.addAttribute("totalshare", totalshare);
 		model.addAttribute("size", size);
		model.addAttribute("user", user.getFname());
		model.addAttribute("totalInvestment", totalInvestment);
		model.addAttribute("totalsharelist",totalsharelist);
		model.addAttribute("companynamelist",companynamelist);
		model.addAttribute("totalamountlist",totalamountlist);
		return "dashboard";
		}
		return "dashboard";
	}
	
	@GetMapping("/riskinfo")
	public String riskInformation(Model model) {
		//List<Company> companylist = new ArrayList();
		Map<String, Double> sectorInvestments = new HashMap<>();
		User user = getCurrentUser();
		
		List<Investment> investment_list = user.getInvestments();
		
		for(Investment investment:investment_list) {
			String sector = investment.getCompany().getSector();
	        double amount = investment.getAmount();
	       sectorInvestments.put(sector, sectorInvestments.getOrDefault(sector, 0.0) + amount);

		}
		
	    // Calculate the total investment amount
	    double totalInvestment = investment_list.stream()
	            .mapToDouble(Investment::getAmount)
	            .sum();

	    // Find the maximum investment amount in a single sector
	    double maxSectorInvestment = sectorInvestments.values().stream()
	            .max(Double::compareTo)
	            .orElse(0.0);

	    // Calculate the diversification percentage
	    double diversificationPercentage = (maxSectorInvestment / totalInvestment) * 100;

	    // Determine the diversification level based on the percentage
	    int diversificationLevel;
	    if (diversificationPercentage >= 50.0) {
	        diversificationLevel = 1;
	    } else if (diversificationPercentage <= 2.0) {
	        diversificationLevel = 10;
	    } else {
	    	double range = 48.0 / 9; 
	        diversificationLevel = 10 - (int) Math.ceil((diversificationPercentage - 2.0) / range);	 
	        }
	    
	    //return diversificationLevel;
        model.addAttribute("diversificationlevel", diversificationLevel);
        model.addAttribute("user", user.getFname());
        

         System.out.println(diversificationLevel);
		return "diversificationgraph";
	}
	
	public User getCurrentUser() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        // Assuming you have a UserRepository to retrieve the user by username
	        return userrepo.findByEmail(userDetails.getUsername());
	    }
	    return null; // Or handle the case where the user is not authenticated
	}
	
}
