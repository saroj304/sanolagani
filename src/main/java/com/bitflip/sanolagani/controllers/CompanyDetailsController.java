package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CompanyDetailsController {
    @Autowired
    CompanyRepo companyRepo;
    @GetMapping("/company")
    public String getAllCompany(Model model){
        List<Integer> companyId = companyRepo.getAllCompany();
        List<Company> companies = new ArrayList<>();
        for(int id: companyId)
            companies.add(companyRepo.getReferenceById(id));

        model.addAttribute("companies", companies);
        return "company-list";
    }

    @GetMapping("/company/{id}")
    public String getCompany(@PathVariable("id") int id,Model model) {
        Company company = companyRepo.getReferenceById(id);
        model.addAttribute("company",company);
        return "company-info";
    }
   
    @GetMapping("/company/details/{id}")
    public String getInvestCompanyDetails(@PathVariable("id") int id,Model model) {
    	Company company = companyRepo.getReferenceById(id);
    	model.addAttribute("company",company);
    	return "details";
    }

   
    @GetMapping("/khalti")
    public String getKhaltiPage() {
    	return "khaltiPayment";
    }
}
