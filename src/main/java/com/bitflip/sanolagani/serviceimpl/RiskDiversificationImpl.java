package com.bitflip.sanolagani.serviceimpl;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.service.RiskDiversificationService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskDiversificationImpl implements RiskDiversificationService{
	 
	public List<Company> recommendCompanies(List<Company> previousInvestments) {
        List<Company> recommendedCompanies = new ArrayList<>();
//        
//        Map<String, Integer> sectorFrequencyMap = new HashMap<>();
//        for (Company company : previousInvestments) {
//            String industrySector = company.getSector();
//            sectorFrequencyMap.put(industrySector, sectorFrequencyMap.getOrDefault(industrySector, 0) + 1);
//        }
//        String mostFrequentSector = "";
//        int maxFrequency = 0;
//        for (Map.Entry<String, Integer> entry : sectorFrequencyMap.entrySet()) {
//            if (entry.getValue() > maxFrequency) {
//                mostFrequentSector = entry.getKey();
//                maxFrequency = entry.getValue();
//            }
//        }
//        for (Company company : previousInvestments) {
//            if (!company.getSector().equals(mostFrequentSector)) {
//                recommendedCompanies.add(company);
//            }
//        }
//
       return recommendedCompanies;
   }

	@Override
	public List<Company> getPreviousInvestmentsForUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		

		
		return null;
	}

}