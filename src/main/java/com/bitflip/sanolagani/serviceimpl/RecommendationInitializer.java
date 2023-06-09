package com.bitflip.sanolagani.serviceimpl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.service.RiskDiversificationService;

@Service
public class RecommendationInitializer {

	@Autowired
	RiskDiversificationService riskdiversification; 
	private boolean bool=false;
	public void getAuthenticUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        bool = authentication.isAuthenticated();
        if(bool) {
        	initializeRecommendations();
        }
    }
 
	
    public void initializeRecommendations() {
        // Assume you have a list of previous investments for a user
        List<Company> previousInvestments = riskdiversification.getPreviousInvestmentsForUser(); // Replace with your logic

        // Get the recommended companies for risk diversification
        //List<String> recommendedCompanies = recommendationService.recommendCompanies(previousInvestments);

        // Pass the recommended companies to the view for rendering
        //storeRecommendedCompanies(recommendedCompanies); // Replace with your logic
    }
}
