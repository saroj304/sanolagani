package com.bitflip.sanolagani.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.CompanyAmountComparator;
import com.bitflip.sanolagani.models.CompanyDateComparator;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.UnverifiedCompanyRepo;
import com.bitflip.sanolagani.service.AdminService;

import java.math.BigDecimal;
import java.security.SecureRandom;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AdminServiceImpl implements AdminService {
	private static final String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int pwd_length = 10;

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	UnverifiedCompanyRepo unverified_repo;
	@Autowired
	CompanyRepo company_repo;
	@Autowired
	UserRepo user_repo;
	private UnverifiedCompanyDetails unverified_details;
	List<Company> moneyList = new ArrayList<>();

	@Override
	public void saveUnverifiedCompany(UnverifiedCompanyDetails un_company) {
		this.unverified_details = un_company;
		unverified_repo.save(un_company);
	}

	@Override
	public List<UnverifiedCompanyDetails> fetchAll() {
		List<UnverifiedCompanyDetails> details = unverified_repo.findAll();
		return details;
	}

	@Override
	public void deleteData(int id) {
		unverified_repo.deleteById(id);

	}

	@Override
	public void saveVerifiedCompany(int id, Company company, User user) {
		String plain_password = generatePassword();
		String encodedPassword = encodePassword(plain_password);
	     unverified_details = unverified_repo.getById(id);
		 sendPasswordEmail(unverified_details.getEmail(), plain_password);//sending password email after regisrtating
	     user.setFname(unverified_details.getFname());
	     user.setLname(unverified_details.getLname());
	     company.setCompanyname(unverified_details.getCompanyname());
	     user.setEmail(unverified_details.getEmail());
	     company.setPhnum(unverified_details.getPhnum());
	     company.setSector(unverified_details.getSector());
	     company.setWebsiteurl(unverified_details.getWebsiteurl());
	     company.setPreviouslyraisedcapital(unverified_details.getRaisedcapital());
	     company.setPrice_per_share(unverified_details.getPrice_per_share());
	     company.setTimespanforraisingcapital(unverified_details.getTimespanforraisingcapital());
	     company.setFilename(unverified_details.getFilename());
	     company.setPan_image_name(unverified_details.getPan_image_name());
	     company.setCitizenship_fname(unverified_details.getCitizenship_fname());
	     company.setCitizenship_bname(unverified_details.getCitizenship_bname());
	     company.setMaximum_quantity(unverified_details.getMaximum_quantity());
	     company.setImage(unverified_details.getImage());
	     System.out.println("the verified image name is"+unverified_details.getImage());
	     user.setPassword(encodedPassword);
		 user.setRole(company.getRole());
		 //user_repo.save(user);
          company.setUser(user);
	     company_repo.save(company);


		unverified_repo.deleteById(id);
	}

	public static String generatePassword() {
		StringBuilder sb = new StringBuilder();
		SecureRandom random = new SecureRandom();

		for (int i = 0; i < pwd_length; i++) {
			int randomIndex = random.nextInt(character.length());
			sb.append(character.charAt(randomIndex));
		}

		return sb.toString();
	}

	public static String encodePassword(String plainPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encoded = encoder.encode(plainPassword);
		boolean isPasswordMatches = encoder.matches(plainPassword, encoded);
		System.out.println(isPasswordMatches);
		return encoded;
	}

	public void sendPasswordEmail(String to, String password) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Company Registered Sucessfully");
		message.setText("your company is sucessfully registered and the authentication details is email:" + to
				+ " password:" + password + ". Regards:seetal raya from sanolagani project");
		mailSender.send(message);
	}

	@Override
	public List<Company> getAllCompany() {
		List<Company> companylist = company_repo.findAll();

		return companylist;
	}

	@Override
	public List<Company> listingBasedonRaisedCapital(List<Company> company) {
		// Sort the companies based on raised capital in descending order     
      Collections.sort(company,new CompanyAmountComparator());
        int a =0;
        for(Company c:company) {
        	a+=1;
        	System.out.println("iam inside Adminserviceimpl class and inside listingbasedonraisedapital method");
        	System.out.println(a);
        	System.out.println(c.getCompanyname());
        	System.out.println(c.getPreviouslyraisedcapital());
        	System.out.println(c.getMaximum_quantity());
        	System.out.println(c.getFilename());
        }
        return company;
}
	public List<Company>listingBasedonRecentDate(List<Company> company){
		Collections.sort(company,new CompanyDateComparator());
		 int a =0;
	        for(Company c:company) {
	        	System.out.println("iam inside Adminserviceimpl class and inside listingbasedonrecentdate method");
	        	a+=1;
	        	System.out.println(a);
	           	System.out.println(c.getPreviouslyraisedcapital());
	        	System.out.println(c.getCreated());
	        }
		return company;
	}
}
