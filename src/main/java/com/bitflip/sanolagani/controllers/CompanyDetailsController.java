package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.Notification;
import com.bitflip.sanolagani.models.TrafficData;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.InvestmentRepo;
import com.bitflip.sanolagani.repository.NotificationRepo;
import com.bitflip.sanolagani.repository.TrafficDataRepo;
import com.bitflip.sanolagani.repository.UserRepo;
import com.bitflip.sanolagani.serviceimpl.RecommendationInitializer;
import com.bitflip.sanolagani.serviceimpl.SentimentPreprocessor;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.LinkedHashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CompanyDetailsController {
	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	UserController usercontroller;
	@Autowired
	InvestmentRepo investrepo;
	@Autowired
	UserRepo userrepo;
	@Autowired
	TrafficDataRepo trafficrepo;
	@Autowired
	NotificationRepo notificationrepo;
	@Autowired
	private SentimentPreprocessor pre;
	@Autowired
	RecommendationInitializer recommedationinit;

//	@GetMapping("/company")
//	public String getAllCompany(Model model) {
//		List<Integer> companyId = companyRepo.getAllCompany();
//		List<Company> companies = new ArrayList<>();
//		for (int id : companyId)
//			companies.add(companyRepo.getReferenceById(id));
//
//		model.addAttribute("companies", companies);
//		return "company-list";
//	}
	@GetMapping("/company")
	public String getAllCompany(Model model) {
		List<Company> companylist = companyRepo.findAll();
		if(companylist.isEmpty()) {
			return "company-list";
		}
		model.addAttribute("companies", companylist);
		return "company-list";
	}

	@GetMapping("/change_password")
	public String changePassword(Model model) {
		String email = (String) model.asMap().get("email");
		model.addAttribute("email", email);
		return "changeinitialpassword";
	}

	@PostMapping("/change/initial/password")
	public String getInitialPasswordChanged(@RequestParam("email") String email,
			@RequestParam("oldpassword") String oldpass,
			@RequestParam("password") String newpassword) {

		User user = userrepo.findByEmail(email);
		System.out.println(email);
		String hashpwd = user.getPassword();
		String plainpwd = oldpass;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean isMatch = passwordEncoder.matches(plainpwd, hashpwd);

		if (isMatch) {
			user.setPassword(passwordEncoder.encode(newpassword));
			user.getCompany().setPwd_change("true");
			userrepo.save(user);
			return "redirect:/home";
		} else {
			System.out.println("Password does not match!");
			return "redirect:/home";

		}

	}

	@GetMapping("/company/{id}")
	public String getCompany(@PathVariable("id") Integer id, Model model,TrafficData trafficdata){
		List<TrafficData> trafficdatalist = trafficrepo.findAll();
		LocalDateTime nowday = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
		String dayString = nowday.format(formatter);
		if(trafficdatalist.isEmpty()) {
			trafficdata.setCompanyid(id);
			trafficdata.setCount(1);
			trafficdata.setVisitmonth(dayString);
			trafficrepo.save(trafficdata);
		}
		else if(trafficdatalist.stream()
				.anyMatch(trafficData -> trafficData.getCompanyid() == id)){
			for (TrafficData traffic:trafficdatalist) {
				String day = traffic.getVisitmonth();
				if(dayString.equals(day)&&traffic.getCompanyid()==id) {
					int count = traffic.getCount();
					count +=1;
					traffic.setCount(count);
					trafficrepo.save(traffic);
					break;
				}else if(!dayString.equals(day)&&traffic.getCompanyid()==id){
					trafficdata.setCompanyid(id);
					trafficdata.setCount(1);
					trafficdata.setVisitmonth(dayString);
					trafficrepo.save(trafficdata);
					break;
				}
			}
		} else {
			trafficdata.setCompanyid(id);
			trafficdata.setCount(1);
			trafficdata.setVisitmonth(dayString);
			trafficrepo.save(trafficdata);

		}

		String status="on limit";
		Company company = companyRepo.getReferenceById(id);
		LocalDateTime now  = LocalDateTime.now();
		LocalDateTime created_date = company.getCreated();
		String time =company.getTimespanforraisingcapital();
		String[] timespansplit = time.split(" ",2);
		int timespan = Integer.parseInt(timespansplit[0]);


		User user = usercontroller.getCurrentUser();
		Integer numberofshare_peruser = investrepo.getTotalQuantityByUserAndCompany(user.getId(), company.getId());
		if (numberofshare_peruser == null) {
			if (now.isAfter(created_date.plusDays(timespan))) {
				status = "time finish";
			}
			model.addAttribute("status", status);
			model.addAttribute("company", company);
			return "company-info";
		} else if (company.getMaximum_quantity() <= numberofshare_peruser) {
			status = "limit reached";
		} else if (now.isAfter(created_date.plusDays(timespan))) {
			status = "time finish";

		}
		model.addAttribute("status", status);
		model.addAttribute("company", company);
		return "company-info";
	}

	@GetMapping("/company/details/{id}")
	public String getInvestCompanyDetails(@PathVariable("id") int id, Model model) {
		Company company = companyRepo.getReferenceById(id);
		User user = usercontroller.getCurrentUser();
		Integer total_quantity_invested = investrepo.getTotalQuantityByUserAndCompany(user.getId(), company.getId());
		int remaining_quantity = company.getMaximum_quantity()-total_quantity_invested;
		model.addAttribute("company", company);
		model.addAttribute("remainingQuantity", remaining_quantity);
		return "details";
	}


	// company dashboard

	@GetMapping("/companydashboard")
	public String gerDashboard(Model model) {
		User user = usercontroller.getCurrentUser();
		int id = user.getCompany().getId();
		LocalDateTime currentDate = LocalDateTime.now();
		Map<String,Integer> pastSixdays = new LinkedHashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");

		for (int i = 6; i >=0; i--) {
			LocalDateTime month = currentDate.minusDays(i);
			String dayString = month.format(formatter);
			System.out.println(dayString);
			pastSixdays.put(dayString, 0);

		}
		List<TrafficData> trafficdatalist = trafficrepo.findAllByCompanyid(id);
		for (TrafficData trafficData : trafficdatalist) {
			String visitMonth = trafficData.getVisitmonth();
			int count = trafficData.getCount();
			pastSixdays.put(visitMonth, count);
		}
		List<String> labels = new ArrayList<>(pastSixdays.keySet());
		List<Integer> trafficvalues = new ArrayList<>(pastSixdays.values());
		int count = notificationrepo.countByCompanyidAndIsreadFalse(id);
		model.addAttribute("unread", count);
		model.addAttribute("labels", labels);
		model.addAttribute("trafficvalues", trafficvalues);

		return "companydashboard";
	}

	@GetMapping("/notification")
	public String getNotification(Model model) {
		User user = usercontroller.getCurrentUser();
		int companyid = user.getCompany().getId();
		List<Notification> notificationlist = notificationrepo.findAllByCompanyid(companyid);
		markNotificationAsRead(notificationlist);
		model.addAttribute("notificationlist", notificationlist);
		return "notification";
	}

	public void markNotificationAsRead(List<Notification> notificationlist) {
		for(Notification notifications:notificationlist) {
			Optional<Notification> optionalNotification = notificationrepo.findById(notifications.getId());
			optionalNotification.ifPresent(notification -> {
				notification.setIsread(true);
				notificationrepo.save(notification);
			});
		}
		}

	
	@GetMapping("/company/investment/details")
	public String getCompanyInvestmentDetails(Model model) {
		Company company = usercontroller.getCurrentUser().getCompany();
        List<Investment> investmentlist = company.getInvestments();
        model.addAttribute("investmentlist", investmentlist);
		return "company_investment-details";
	}
	
	@GetMapping("/company/sector/status")
	public String getCompanySortByStatusAndSector(@PathVariable("sector") String sector,
			@PathVariable("status") String status,Model model) {
	        
		List<Company> sectorstatuslist = new ArrayList<>();
		
		if(status == "Highest Raised") {
	        List<Company> basedoncapital  = companyRepo.findAll();
	        Collections.sort(basedoncapital, Comparator.comparing(Company::getPreviouslyraisedcapital).reversed());
            for(Company company:basedoncapital) {
        	   if(company.getSector().equalsIgnoreCase(sector)) {
        		 sectorstatuslist.add(company);
        	   }
             }
		}
		else if(status =="Recently Launched") {
             List<Company> basedondate = companyRepo.findAllCompanyBasesOnCreationalDates();
             for(Company company:basedondate) {
          	   if(company.getSector().equalsIgnoreCase(sector)) {
          		 sectorstatuslist.add(company);
          	   }
             }
          }
		else if(status =="Feedback") {
              List<Company> basedonfeedback = pre.getCompaniesWithGoodSentiment();
              for(Company company:basedonfeedback) {
           	   if(company.getSector().equalsIgnoreCase(sector)) {
           		 sectorstatuslist.add(company);
           	   }
              }
           }  
		else if(status =="Diversification") {
	        List<Company> basedondiversification = recommedationinit.getRecommendCompanies();
            for(Company company:basedondiversification) {
         	   if(company.getSector().equalsIgnoreCase(sector)) {
         		 sectorstatuslist.add(company);
         	   }
            }
         } 
		model.addAttribute("sectorstatuslist", sectorstatuslist);
		return "company-list";
	}
	
	
}
