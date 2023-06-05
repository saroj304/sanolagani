package com.bitflip.sanolagani.controllers;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.codec.digest.DigestUtils;

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.service.UserService;
import com.bitflip.sanolagani.serviceimpl.EmailService;
import java.util.Map;
import java.util.HashMap;


=======

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.service.UserService;
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb


@Controller
public class RegisterController {
	@Autowired
	UserService userservice;
	@Autowired
	BCryptPasswordEncoder passwordencoder;
<<<<<<< HEAD
	@Autowired
	EmailService emailservice;

    private Map<String, String> otpStore = new HashMap<>();
=======
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
	
	
	@GetMapping("/register")
	public String registerPage(@ModelAttribute("user")User user) {
		return "signup";
	}
	
	@PostMapping("/register")
	public String saveUser(@Valid @ModelAttribute("user")User user ,BindingResult result) {
<<<<<<< HEAD
		if(result.hasErrors()) {
			return "signup";
		}
		user.setPassword(passwordencoder.encode(user.getPassword()));
		String otp = emailservice.sendEmail(user.getEmail());
		otpStore.put(user.getEmail(), hashOTP(otp));

		return "otp";
		
	}
	@PostMapping("/otpverify")
	public String otpVerify(@RequestParam("otp") int otp, @RequestParam("email") String email) {
		String useremail = email;
		String userotp = Integer.toString(otp);
		
		System.out.println(otpStore);
		if (otpStore.containsKey(useremail)) {
            String storedOTP = otpStore.get(useremail);

            if (storedOTP.equals(hashOTP(userotp))) {
                otpStore.remove(useremail); // OTP matched, remove it from store
                return "login";
            }
        }

		return "index";
    }
		

	  private String hashOTP(String otp) {
	        return DigestUtils.sha256Hex(otp); // Hash the OTP for storage and comparison
	    }
=======
		System.out.println(result.hasErrors());
		if(result.hasErrors()) {
			return"signup";
		}
		user.setPassword(passwordencoder.encode(user.getPassword()));
		userservice.saveUser(user);
		return "index";
	}
>>>>>>> 8a7b388a6ae8b3d8640cc12480d406e99105cbeb
}
