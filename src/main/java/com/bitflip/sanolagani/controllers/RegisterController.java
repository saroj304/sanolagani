package com.bitflip.sanolagani.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.apache.commons.codec.digest.DigestUtils;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.models.User;

import com.bitflip.sanolagani.service.UserService;
import com.bitflip.sanolagani.serviceimpl.EmailService;
import java.util.Map;
import java.util.HashMap;



@Controller
public class RegisterController {
	@Autowired
	UserService userservice;
	@Autowired
	BCryptPasswordEncoder passwordencoder;
	@Autowired
	EmailService emailservice;
	
	private Map<String, String> otpStore = new HashMap<>();
	private User user;
	private UnverifiedCompanyDetails unverified_details;

	@GetMapping("/register")
	public String registerPage(@ModelAttribute("user") User user) {
		return "user_signup";
	}

	@PostMapping("/register")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
		this.user = user;
		if (result.hasErrors()) {
			return "user_signup";
		}
		user.setPassword(passwordencoder.encode(user.getPassword()));
       // Role role=rolerepo.findByName("USER");
        //System.out.println(role.getName());
        //user.addRole(role);
		String otp = emailservice.sendEmail(user.getEmail());
		otpStore.put(user.getEmail(), hashOTP(otp));

		return "otp";

	}

	@PostMapping("/otpverify")
	public String otpVerify(@RequestParam("otp") int otp, @RequestParam("email") String email) {
		String useremail = email;
		String userotp = Integer.toString(otp);
		if (otpStore.containsKey(email)) {
			String storedOTP = otpStore.get(useremail);

			if (storedOTP.equals(hashOTP(userotp))) {
				otpStore.remove(email); // OTP matched, remove it from store
//			     for(Role r:user.getRole()) {
//			    	 System.out.println(r.getName());
//			     }
				System.out.println(user.getRole());
		   	userservice.saveUser(user);
				return "user_login";
			}
		}

		return "index";
    }
	  private String hashOTP(String otp) {
	        return DigestUtils.sha256Hex(otp); // Hash the OTP for storage and comparison
	    }
	  
	  // company register
	  


		@GetMapping("/companyregister")
		public String companySignupPage() {
			return "company_registration";
		}
		@PostMapping("/companyverify")
		public String verifyCompany(@Valid @ModelAttribute("company") UnverifiedCompanyDetails un_company,
				                  HttpServletRequest request,
				                  BindingResult result) {
			try {
			String results=emailservice.verifyCompanyDetails(un_company, request, result);
			if(results.equalsIgnoreCase("success")) {
				System.out.println(results);
				return "user_login";
			}
			else {
				System.out.println(results);

				return "user_signup";
			}
			}catch(Exception e ) {
				e.printStackTrace();
			}
			return null;
}}
