package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CompanyDetailsController {
    @Autowired
    CompanyRepo companyRepo;
    @GetMapping("/company/{id}")
    public String getCompany(@PathVariable("id") Integer id, Model model){
        Company company = companyRepo.getReferenceById(id);
        String name = company.getCompanyname();
        String image = company.getImage();
        model.addAttribute("company", company);
        return "company-info";
    }
}
