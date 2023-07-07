package com.bitflip.sanolagani.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.codec.digest.DigestUtils;

import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.models.User;

import com.bitflip.sanolagani.service.UserService;
import com.bitflip.sanolagani.serviceimpl.EmailService;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Controller
public class RegisterController {
	@Autowired
	UserService userservice;
	@Autowired
	BCryptPasswordEncoder passwordencoder;
	@Autowired
	EmailService emailservice;
	
	private Map<String, String> otpStore = new HashMap<>();
	private List<User> userstore = new ArrayList<>();
	//private User user;
	private UnverifiedCompanyDetails unverified_details;

	@GetMapping("/register")
	public String registerPage(@ModelAttribute("user") User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null|| authentication.getName().equals("anonymousUser")) {
		return "user_signup";
	}else {
		return "redirect:/home";
	}
	}

	@PostMapping("/register")
	public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result,RedirectAttributes redirectAttributes) {
		//this.user = user;
		userstore.add(user);
		if (result.hasErrors()) {
			return "user_signup";
		}
		user.setPassword(passwordencoder.encode(user.getPassword()));
		String otp = emailservice.sendEmail(user.getEmail());
		otpStore.put(user.getEmail(), hashOTP(otp));
		redirectAttributes.addFlashAttribute("email", user.getEmail());
		return "redirect:/otpverify";

	}

	@PostMapping("/otpverify")
	public String otpVerify(@RequestParam("otp") int otp, @RequestParam("email") String email,RedirectAttributes redirectAttributes) {
		boolean result = userservice.checkEmail(email);
		String useremail = email;
		String userotp = Integer.toString(otp);
		if (otpStore.containsKey(email)) {
			String storedOTP = otpStore.get(useremail);
			if (storedOTP.equals(hashOTP(userotp))) {
				if(result) {
					redirectAttributes.addFlashAttribute("email", email);
					return "redirect:/changepassword";
				}
             for(User user:userstore) {
            	 if(user.getEmail().equals(email)) {
		   	        userservice.saveUser(user);
            	 }
		   	    otpStore.remove(email);
             }
				return "user_login";
			}
		}
		redirectAttributes.addFlashAttribute("email", email);
		return "redirect:/otpverify";
    }
	  private String hashOTP(String otp) {
	        return DigestUtils.sha256Hex(otp); // Hash the OTP for storage and comparison
	    }
	  
	  // company register
	  


		@GetMapping("/companyregister")
		public String companySignupPage(@ModelAttribute("un_company") UnverifiedCompanyDetails un_company) {
			return "company_registration";
		}
		@PostMapping("/companyverify")
		public String verifyCompany(@Valid @ModelAttribute("un_company") UnverifiedCompanyDetails un_company, BindingResult result,
				                  HttpServletRequest request,Model model
				                 ) {
		
			if(result.hasErrors()) {
				model.addAttribute("error","*Please fill all the fields!!");
				List<ObjectError> ERR=result.getAllErrors();
				for(ObjectError o:ERR) {
				}
				return "company_registration";
			}
			try {
				String results=emailservice.verifyCompanyDetails(un_company, request, result);
					if(results.equalsIgnoreCase("success")) {
						return "redirect:/login";
					}
					else {
						return "company_registration";
					}
					}catch(Exception e ) {
						e.printStackTrace();
					}
			return null;
}
		@GetMapping("/forgotpassword")
    	public String resetPasswordPage() {
	  return "resetpassword";
  }
        @PostMapping("/resetpassword")
        public String resetPassword(@RequestParam("email") String email,RedirectAttributes redirectAttributes) {
        	boolean result = userservice.checkEmail(email);
        	if(result) {
             String otps=emailservice.sendEmail(email);
             otpStore.put(email, hashOTP(otps));
        	redirectAttributes.addFlashAttribute("email", email);
        	return "redirect:/otpverify";
        	}else {
        		return "redirect:/forgotpassword";
        	}
           
        }
        @GetMapping("/changepassword")
    	public String changePassword(Model model) {
        String email = (String) model.asMap().get("email");
        model.addAttribute("email", email);
	      return "changepassword";
  }
        @GetMapping("/otpverify")
    	public String otpVerify(Model model,RedirectAttributes redirectAttributes) {
        	String email = (String) model.asMap().get("email");
        	redirectAttributes.addFlashAttribute("email", email);
        	model.addAttribute("email", email);
	         return "otp";
  }
        
        @PostMapping("/updatepassword")
        public String updatePassword(@RequestParam("email") String email,
        		                   @RequestParam("password") String password
        		                       ) {
        	boolean result = true;
        	userservice.updatePassword(email,password,result);
        	return "redirect:/login";
        }
        @GetMapping("/raisecapital")
        public String companyRegister() {
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    		if (authentication == null|| authentication.getName().equals("anonymousUser")) {
        	return "company_registration";
    		}
    		else {
    			return "redirect:/home";
    		}
        }
        
}
