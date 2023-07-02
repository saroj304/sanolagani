package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.InvestmentRepo;
import com.bitflip.sanolagani.repository.UserRepo;

import java.time.LocalDateTime;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompanyDetailsController {
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    UserController usercontroller;
    @Autowired
    InvestmentRepo investrepo;
    @Autowired
    UserRepo userrepo;
    
    
    @GetMapping("/change_password")
    public String changePassword(Model model) {
    	String email = (String) model.asMap().get("email");
         model.addAttribute("email", email);
    	return "changeinitialpassword";
    }
    @PostMapping("/change/initial/password")
    public String getInitialPasswordChanged(@RequestParam("email") String email,
    		                                @RequestParam("oldpassword") String oldpass,
    		                                @RequestParam("password") String newpassword) {
    	                         
    	
    	User user = userrepo.findByEmail(email);
    	System.out.println(email);
    	String hashpwd = user.getPassword();
    	String  plainpwd = oldpass;
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	boolean isMatch = passwordEncoder.matches(plainpwd, hashpwd);

    	if (isMatch) {
    	    user.setPassword(passwordEncoder.encode(newpassword));
    	    user.getCompany().setPwd_change("true");
    	    userrepo.save(user);
    	    return "redirect:/home";
    	} else {
    	    System.out.println("Password does not match!");
    	    return "redirect:/home";

    	}
    	
    	
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
