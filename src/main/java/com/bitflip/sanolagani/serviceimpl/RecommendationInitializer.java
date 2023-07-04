package com.bitflip.sanolagani.serviceimpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.service.RiskDiversificationService;

@Service
public class RecommendationInitializer {

	@Autowired
	RiskDiversificationService riskdiversification; 
	private String bool;
	public List<Company> getRecommendCompanies() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null|| authentication.getName().equals("anonymousUser")) {
        	return null;	
        }
        List<Company> company_list=initializeRecommendations();
    	return company_list;
    }
	
    public List<Company> initializeRecommendations() {
      List<Company> previousInvestments = riskdiversification.getPreviousInvestmentsForUser(); // Replace with your logic
      

         //Get the recommended companies for risk diversification
        List<Company> recommendedCompanies = riskdiversification.recommendCompanies(previousInvestments);
        // Pass the recommended companies to the view for rendering
         return recommendedCompanies; // Replace with your logic
    }
    }

