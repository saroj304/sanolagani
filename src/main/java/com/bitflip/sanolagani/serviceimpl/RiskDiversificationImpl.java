package com.bitflip.sanolagani.serviceimpl;

import com.bitflip.sanolagani.controllers.UserController;
import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Investment;
import java.util.Optional;


import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.InvestmentRepo;
import com.bitflip.sanolagani.repository.CompanyRepo;

import com.bitflip.sanolagani.repository.UserRepo;
import com.bitflip.sanolagani.service.RiskDiversificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashSet;

import java.util.Map;

@Service
public class RiskDiversificationImpl implements RiskDiversificationService{
	@Autowired
	InvestmentRepo investment_repo;
	@Autowired
	UserRepo user_repo;
	@Autowired 
	CompanyRepo company_repo;
	@Autowired
	UserController usercontroller;

	 
	public List<Company> recommendCompanies(List<Company> previousInvestments) {
		User user = usercontroller.getCurrentUser();
        List<Company> recommendedCompanies = new ArrayList<>();
        Map<String, Integer> sectorFrequencyMap = new HashMap<>();
        for (Company company : previousInvestments) {
            String industrySector = company.getSector();
           if(sectorFrequencyMap.containsKey(industrySector)) {
        	   int existing_value = sectorFrequencyMap.get(industrySector);
        	    sectorFrequencyMap.put(industrySector, existing_value+(int)company.getInvestments().stream()
                                                                     .filter(investment -> investment.getUser().getId() == user.getId())
                                                                     .filter(investment -> investment.getCompany().getSector().equals(industrySector))
                                                                     .mapToDouble(Investment::getAmount)
                                                                      .sum());
           }else {
            sectorFrequencyMap.put(industrySector,(int)company.getInvestments().stream()
                                                    .filter(investment -> investment.getUser().getId() == user.getId())
                                                    .filter(investment -> investment.getCompany().getSector().equals(industrySector))
                                                    .mapToDouble(Investment::getAmount)
                                                    .sum());
        }
           }
        
        String mostFrequentSector = "";
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : sectorFrequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentSector = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
    	List<Company> companylist = company_repo.findAll();
		for (Company company : companylist) {
		    boolean isInvested = false;
		    for (Company investedCompany : previousInvestments) {
		        if (investedCompany.getSector().equals(company.getSector())) {
		            isInvested = true;
		            break;
		        }
		    }
		    if (!isInvested) {
		        recommendedCompanies.add(company);
		    }
		}	
        for (Company company : previousInvestments) {
            if (!company.getSector().equals(mostFrequentSector)) {
                recommendedCompanies.add(company);
            }
        }

			
		
       return recommendedCompanies;
   }

	@Override
	public List<Company> getPreviousInvestmentsForUser() {
		List<Integer> company_ids = new ArrayList<>();
		LinkedHashSet<Company> company_list = new LinkedHashSet<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user= user_repo.findByEmail(username);
		if(user !=null) {
			 List<Investment> investment_list = user.getInvestments();
			 for(Investment investment:investment_list) {
				  company_ids.add(investment.getCompany().getId());
			 	  	
		}
		       }
		        
		   for(int company_id:company_ids) {

		   Company company = company_repo.getReferenceById(company_id);

		   company_list.add(company);
		    
	   }
	       List<Company> companies_list = new ArrayList<>(company_list);
		   
		return companies_list;
	}

}