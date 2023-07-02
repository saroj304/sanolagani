package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.InvestmentRepo;
import java.time.LocalDateTime;

import java.util.List;

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
    @Autowired
    UserController usercontroller;
    @Autowired
    InvestmentRepo investrepo;
    @GetMapping("/company")
    public String getAllCompany(Model model) {
        List<Integer> companyId = companyRepo.getAllCompany();
        List<Company> companies = new ArrayList<>();
        for (int id : companyId)
            companies.add(companyRepo.getReferenceById(id));

        model.addAttribute("companies", companies);
        return "company-list";
    }
    
    @GetMapping("/company/{id}")
    public String getCompany(@PathVariable("id") Integer id, Model model){
    	String status="on limit";
        Company company = companyRepo.getReferenceById(id);
    	LocalDateTime now  = LocalDateTime.now();
    	LocalDateTime created_date = company.getCreated();
    	String time =company.getTimespanforraisingcapital();
    	String[] timespansplit = time.split(" ",2);
    	int timespan = Integer.parseInt(timespansplit[0]);
    	System.out.println(company.getCompanyname()+" "+timespan);
        
        User user = usercontroller.getCurrentUser();
        Integer numberofshare_peruser = investrepo.getTotalQuantityByUserAndCompany(user.getId(), company.getId());
       if(numberofshare_peruser==null) {
    	   if(now.isAfter(created_date.plusDays(timespan)) ) {
           	status = "time finish";
           }
    	   model.addAttribute("status",status);
           model.addAttribute("company", company);
           return "company-info";
       }
       else if(company.getMaximum_quantity()<=numberofshare_peruser) {
    	   status = "limit reached";
       } else if(now.isAfter(created_date.plusDays(timespan)) ) {
        	    status = "time finish";

       }	   
        model.addAttribute("status",status);
        model.addAttribute("company", company);
        return "company-info";
    }

   
    @GetMapping("/company/details/{id}")
    public String getInvestCompanyDetails(@PathVariable("id") int id,Model model) {
    	Company company = companyRepo.getReferenceById(id);
    	model.addAttribute("company",company);
    	return "details";
    }
}
