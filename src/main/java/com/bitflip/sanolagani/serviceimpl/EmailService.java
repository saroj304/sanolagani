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
<<<<<<< HEAD
    
    
=======
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9

    public String sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        otp = Integer.toString(generateOtp());
        message.setTo(to);
        message.setSubject("OTP for registration");
        message.setText("your otp for the registration is "+ otp);
        mailSender.send(message);
        return otp;
<<<<<<< HEAD
     
=======
        //verifyOtp(otp,to);
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9
    }
    public int generateOtp() {
    	Random rand = new Random();
    	int randomNum = rand.nextInt(999999 - 111111 + 1) + 111111;
    	return randomNum;
    }
    
<<<<<<< HEAD

    
    
=======
    
    public void verifyOtp(int otp,String to) {
    	
    }
>>>>>>> 8cb02cfe1ef9f59cee1e34312d8903924f6730c9
}