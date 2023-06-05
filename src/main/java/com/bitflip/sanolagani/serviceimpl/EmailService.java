package com.bitflip.sanolagani.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private String otp;
    
    

    public String sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        otp = Integer.toString(generateOtp());
        message.setTo(to);
        message.setSubject("OTP for registration");
        message.setText("your otp for the registration is "+ otp);
        mailSender.send(message);
        return otp;
     
    }
    public int generateOtp() {
    	Random rand = new Random();
    	int randomNum = rand.nextInt(999999 - 111111 + 1) + 111111;
    	return randomNum;
    }
    

    
    
}