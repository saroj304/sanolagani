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
import java.io.FileOutputStream;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
        message.setSubject("One Time Password");
        message.setText("Dear Sir/Madam,"
        		+ "\n \nATTN : Please do not reply to this email.This mailbox is not monitored and you will not receive a response.\n"
        		+ "\n \nYour One Time Password (OTP ) is "+ otp+"."
        		+ " If you have any queries, Please contact us at,\n"
        		+ "\n"
        		+ " sanolagani investment firm,\n"
        		+ " guwarko,lalitpur, Nepal.\n"
        		+ " Phone # 977-98123456789\n"
        		+ " Email Id: support@sanolagani.com.np\n"
        		+ " Warm Regards,\n"
        		+ " sanolagani investment firm.");

<<<<<<< HEAD
<<<<<<< HEAD

=======
>>>>>>> 6fe5254ad5c23402ee91f373707a89aa316a0d6a
        mailSender.send(message);

=======
       //mailSender.send(message);
>>>>>>> 6c18287d761bc8ce34aa903283ca11223ec6942a

        return otp;
    }

    public int generateOtp() {
    	Random rand = new Random();
    	int randomNum = rand.nextInt(999999 - 111111 + 1) + 111111;
    	return randomNum;
    }
    public String verifyCompanyDetails(@Valid @ModelAttribute("unverifiedcompany")UnverifiedCompanyDetails un_company,
                              HttpServletRequest request,
                              BindingResult result)  throws MessagingException, IOException {

      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      MultipartFile pdfFile = multipartRequest.getFile("pdfFile");
      MultipartFile citizen_front = multipartRequest.getFile("citizen_front");
      MultipartFile citizen_back = multipartRequest.getFile("citizen_back");
      MultipartFile register_photo = multipartRequest.getFile("companypan");
      MultipartFile company_image  = multipartRequest.getFile("com_image");
      

      //to store on the secondary memory  
      String pdf_name = un_company.getCompanyname()+"_"+pdfFile.getOriginalFilename();
      String citizen_front_name = un_company.getCompanyname()+"_"+citizen_front.getOriginalFilename();
      String citizen_back_name = un_company.getCompanyname()+"_"+citizen_back.getOriginalFilename();
      String register_photo_name = un_company.getCompanyname()+"_"+register_photo.getOriginalFilename();
      String company_img = un_company.getCompanyname()+"_"+company_image.getOriginalFilename();
      
      //to retrieve extension
      String citizen_front_name_extension = citizen_front_name.substring(citizen_front_name.lastIndexOf(".") + 1);
      String citizen_back_name_extension = citizen_back_name.substring(citizen_back_name.lastIndexOf(".") + 1);
      String register_photo_name_extension = register_photo_name.substring(register_photo_name.lastIndexOf(".")+1);
      //String company_photo_name_extension = company_img.substring(company_img.lastIndexOf(".")+1);

      
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      
      helper.setTo("raayaseetal@gmail.com");
      helper.setSubject("company registration details");
      helper.setText("Registration request for the company having name :"+un_company.getCompanyname());

      helper.addAttachment("company_audit_report.pdf", pdfFile);
      helper.addAttachment("citizenship_front."+citizen_front_name_extension.toLowerCase(), citizen_front);
      helper.addAttachment("citizenship_back."+citizen_back_name_extension, citizen_back);
      helper.addAttachment("company_register_details."+register_photo_name_extension, register_photo);
      mailSender.send(message);
      
     
      try {

    		byte[] citizen_f = citizen_front.getBytes();
    		byte[] citizen_b= citizen_back.getBytes();
    		byte[] register = register_photo.getBytes();
    		byte[] com_image = company_image.getBytes();
<<<<<<< HEAD

=======
    		
>>>>>>> 6c18287d761bc8ce34aa903283ca11223ec6942a
    		String path = "../sanolagani/src/main/resources/static/unverified_details/";
            File uploadedFile = new File("../sanolagani/src/main/resources/static/unverified_details/" + pdf_name);

            FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
            fileOutputStream.write(pdfFile.getBytes());
            fileOutputStream.close();

<<<<<<< HEAD
=======


    		
>>>>>>> 6c18287d761bc8ce34aa903283ca11223ec6942a
    	   Path company_i_path = Paths.get(path+company_img);
    	   Files.write(company_i_path, com_image);
    	   
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
    	   un_company.setImage(company_img);

    	   System.out.println("unverified company is going to be registered");
    	   System.out.println(un_company);

    	   adminservice.saveUnverifiedCompany(un_company);
    	 
    	}catch(Exception e) {
        e.printStackTrace();
    	}
      return "success";
}
    
}



