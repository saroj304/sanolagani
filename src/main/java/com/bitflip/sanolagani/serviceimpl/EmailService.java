package com.bitflip.sanolagani.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.nio.file.Files;
import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.repository.UnverifiedCompanyRepo;
import com.bitflip.sanolagani.service.AdminService;

import java.io.IOException;


import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AdminService adminservice;
    
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
    public String verifyCompanyDetails(@Valid @ModelAttribute("company")UnverifiedCompanyDetails un_company,
                              HttpServletRequest request,
                              BindingResult result)  throws MessagingException, IOException {

      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      MultipartFile pdfFile = multipartRequest.getFile("pdfFile");
      MultipartFile citizen_front = multipartRequest.getFile("citizen_front");
      MultipartFile citizen_back = multipartRequest.getFile("citizen_back");
      MultipartFile register_photo = multipartRequest.getFile("companypan");
      
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      
      helper.setTo("raayaseetal@gmail.com");
      helper.setSubject("company registration details");
      helper.setText("Registration request for the company having name :"+un_company.getCompanyname());

      helper.addAttachment("company_audit_report", pdfFile);
      helper.addAttachment("citizenship_front", citizen_front);
      helper.addAttachment("citizenship_back", citizen_back);
      helper.addAttachment("company_register_details", register_photo);
      mailSender.send(message);
      
      //to store on the secondary memory  
      String pdf_name = un_company.getCompanyname()+"_"+un_company.getId()+"_"+ pdfFile.getOriginalFilename();
      String citizen_front_name = un_company.getCompanyname()+"_"+un_company.getId()+"_"+citizen_front.getOriginalFilename();
      String citizen_back_name = un_company.getCompanyname()+"_"+un_company.getId()+"_"+citizen_back.getOriginalFilename();
      String register_photo_name = un_company.getCompanyname()+"_"+un_company.getId()+"_"+register_photo.getOriginalFilename();
      try {

    		byte[] citizen_f = citizen_front.getBytes();
    		byte[] citizen_b= citizen_back.getBytes();
    		byte[] register = register_photo.getBytes();
    		
    		String pdf_path = "/home/seetal/Desktop/sanolagani/src/main/resources/static/unverified_details/"+pdf_name;
    		String path = "/home/seetal/Desktop/sanolagani/src/main/resources/static/unverified_details/";
    		
    		File dest = new File(pdf_path);
    		pdfFile.transferTo(dest);
    		
    	   
    	   Path citizenf_path = Paths.get(path+citizen_front_name);
    	   Files.write(citizenf_path, citizen_f);
    	   
    	   Path citizenb_path = Paths.get(path+citizen_back_name);
    	   Files.write(citizenb_path, citizen_b);
    	   
    	   Path regiser_path = Paths.get(path+register_photo_name);
    	   Files.write(regiser_path, register);
    	   
    	   un_company.setFilename(pdf_name);
    	   un_company.setCitizenship_fname(citizen_front_name);
    	   un_company.setCitizenship_bname(citizen_back_name);
    	   un_company.setPan_image_name(register_photo_name);
    	  adminservice.saveUnverifiedCompany(un_company);
    	  System.out.println("sucess");
    	 
    	}catch(Exception e) {
        e.printStackTrace();
    	}
      return "success";
}
    

}



