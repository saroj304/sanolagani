package com.bitflip.sanolagani.serviceimpl;

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
import java.util.Map;

@Service
public class RiskDiversificationImpl implements RiskDiversificationService{
	@Autowired
	InvestmentRepo investment_repo;
	@Autowired
	UserRepo user_repo;
	@Autowired 
	CompanyRepo company_repo;
	
	List<Integer> company_ids = new ArrayList<>();
	List<Company> company_list = new ArrayList<>();

	 
	public List<Company> recommendCompanies(List<Company> previousInvestments) {
        List<Company> recommendedCompanies = new ArrayList<>();
        Map<String, Integer> sectorFrequencyMap = new HashMap<>();
        for (Company company : previousInvestments) {
            String industrySector = company.getSector();
            sectorFrequencyMap.put(industrySector, sectorFrequencyMap.getOrDefault(industrySector, 0) + 1);
            
        }
        String mostFrequentSector = "";
        int maxFrequency = 0;
        for (Map.Entry<String, Integer> entry : sectorFrequencyMap.entrySet()) {
        	
            if (entry.getValue() > maxFrequency) {
                mostFrequentSector = entry.getKey();

                maxFrequency = entry.getValue();

            }
        }
        for (Company company : previousInvestments) {
            if (!company.getSector().equals(mostFrequentSector)) {
                recommendedCompanies.add(company);
            }
        }

			List<Company> companylist = company_repo.findAll();
			
			// Iterate over all fetched companies and check if they are present in the previously invested companies list
			for (Company company : companylist) {
			    boolean isInvested = false;

			    // Check if the company is present in the previously invested companies list
			    for (Company investedCompany : previousInvestments) {
			        if (investedCompany.getId()==company.getId()) {
			            isInvested = true;
			            break;
			        }
			    }

			    // If the company is not invested, add it to the non-invested companies list
			    if (!isInvested) {
			        recommendedCompanies.add(company);
			    }
			}		
		
       return recommendedCompanies;
   }

	@Override
	public List<Company> getPreviousInvestmentsForUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user= user_repo.findByEmail(username);
		if(user !=null) {
		   int id = user.getId();
			   List<Investment> investment_list=investment_repo.findAll();
		       for(Investment investment:investment_list) {
			   if(investment.getUser().getId()==id) {
				  company_ids.add(investment.getCompany().getId());
				  
		
		}
		       }
		      
		       
		   for(int company_id:company_ids) {

		   Company company = company_repo.getById(company_id);

		   company_list.add(company);
		     
	   }
	       }
		   
		
		return company_list;
	}

}