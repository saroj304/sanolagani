package com.bitflip.sanolagani.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyDetailsController {
    @GetMapping("/company")
    public String getCompany(){
        return "company-info";
    }
}
