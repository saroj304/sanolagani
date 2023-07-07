package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.CompanyArticles;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.Notification;
import com.bitflip.sanolagani.models.TrafficData;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.*;
import com.bitflip.sanolagani.serviceimpl.RecommendationInitializer;
import com.bitflip.sanolagani.serviceimpl.SentimentPreprocessor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.LinkedHashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    CompanyArticlesRepo articlesRepo;

    @Autowired
    private SentimentPreprocessor pre;
    @Autowired
    RecommendationInitializer recommedationinit;

    @GetMapping("/company")
    public String getAllCompany(Model model) {
        List<Company> companylist = companyRepo.findAll();
        if (companylist.isEmpty()) {
            return "company-list";
        }
        model.addAttribute("companies", companylist);
        return "company-list";
    }

    @GetMapping("/change_password")
    public String changePassword(Model model) {
        String email = (String) model.asMap().get("email");
        if (email == null) {
            User user = usercontroller.getCurrentUser();
            model.addAttribute("email", user.getEmail());
            return "changeinitialpassword";

        }
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

        boolean matchcurrentandold = passwordEncoder.matches(newpassword, hashpwd);
        if (matchcurrentandold) {
            System.out.println("You cannot use the same password as old");
            return "redirect:/change_password";
        }

        if (isMatch && user.getRole().equalsIgnoreCase("COMPANY")) {
            user.setPassword(passwordEncoder.encode(newpassword));
            user.getCompany().setPwd_change("true");
            userrepo.save(user);
            return "redirect:/home";
        } else if (isMatch && user.getRole().equalsIgnoreCase("USER")) {
            user.setPassword(passwordEncoder.encode(newpassword));
            userrepo.save(user);
            return "redirect:/home";
        } else {
            System.out.println("Password does not match!");
            return "redirect:/change_password";

        }

    }
    @GetMapping("/company/{id}")
    public String getCompany(@PathVariable("id") Integer id, Model model, TrafficData trafficdata) {
        List<TrafficData> trafficdatalist = trafficrepo.findAll();
        LocalDateTime nowday = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
        String dayString = nowday.format(formatter);
        if (trafficdatalist.isEmpty()) {
            trafficdata.setCompanyid(id);
            trafficdata.setCount(1);
            trafficdata.setVisitmonth(dayString);
            trafficrepo.save(trafficdata);
        } else if (trafficdatalist.stream()
                .anyMatch(trafficData -> trafficData.getCompanyid() == id)) {
            for (TrafficData traffic : trafficdatalist) {
                String day = traffic.getVisitmonth();
                if (dayString.equals(day) && traffic.getCompanyid() == id) {
                    int count = traffic.getCount();
                    count += 1;
                    traffic.setCount(count);
                    trafficrepo.save(traffic);
                    break;
                } else if (!dayString.equals(day) && traffic.getCompanyid() == id) {
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

        String status = "on limit";
        User user = usercontroller.getCurrentUser();
        Company company = companyRepo.getReferenceById(id);
        List<CompanyArticles> allArticles = articlesRepo.findByCompanyId(id);

        model.addAttribute("articles", allArticles);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime created_date = company.getCreated();
        String time = company.getTimespanforraisingcapital();
        String[] timespansplit = time.split(" ", 2);
        int timespan = Integer.parseInt(timespansplit[0]);

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
        int remaining_quantity = company.getMaximum_quantity() - total_quantity_invested;
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
        Map<String, Integer> pastSixdays = new LinkedHashMap<>();
        Map<String, Double> pastSixdaysinvestmentamount = new LinkedHashMap<>();
        Map<String, Integer> totalUsersInvestedMap = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");

        for (int i = 6; i >= 0; i--) {
            LocalDateTime month = currentDate.minusDays(i);
            String dayString = month.format(formatter).toLowerCase();
            pastSixdays.put(dayString, 0);
            pastSixdaysinvestmentamount.put(dayString, 0.0);
            totalUsersInvestedMap.put(dayString, 0);
        }
        List<TrafficData> trafficdatalist = trafficrepo.findAllByCompanyid(id);
        if (!trafficdatalist.isEmpty()) {
            for (TrafficData trafficData : trafficdatalist) {
                String visitMonth = trafficData.getVisitmonth().toLowerCase();
                int count = trafficData.getCount();
                pastSixdays.put(visitMonth, count);
            }

        }

        List<Investment> investmentlist = investrepo.findAllByCompany_id(id);
        if (!investmentlist.isEmpty()) {
            String dayname = "";
            for (Investment investment : investmentlist) {
                LocalDateTime investedtime = investment.getInvestment_date_time();
                LocalDate dates = investedtime.toLocalDate();
                Date date = Date.valueOf(dates);
                DayOfWeek dayofweek = investedtime.getDayOfWeek();
                if (!dayname.equalsIgnoreCase(dayofweek.name())) {
                    dayname = dayofweek.name().toLowerCase();
                    double amount = investrepo.getTotalInvestedByDate(date);
                    pastSixdaysinvestmentamount.put(dayname, amount);
                    int useracquistionnumber = investrepo.getTotalInvestedUserByDate(date);
                    totalUsersInvestedMap.put(dayname, useracquistionnumber);

                }

            }

        }
        List<Double> investedamount = new ArrayList<>(pastSixdaysinvestmentamount.values());
        model.addAttribute("investedamount", investedamount);

        List<Integer> totaluseracquisition = new ArrayList<>(totalUsersInvestedMap.values());
        model.addAttribute("totaluseracquisition", totaluseracquisition);

        List<Integer> trafficvalues = new ArrayList<>(pastSixdays.values());
        model.addAttribute("trafficvalues", trafficvalues);

        List<String> labels = new ArrayList<>(pastSixdays.keySet());
        int count = notificationrepo.countByCompanyidAndIsreadFalse(id);

        model.addAttribute("unread", count);
        model.addAttribute("labels", labels);
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
        for (Notification notifications : notificationlist) {
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

	@GetMapping("/company/{sector}/{status}")
	public String getCompanySortByStatusAndSector(@PathVariable("sector") String sector,
			@PathVariable("status") String status,Model model) {
	        
		List<Company> sectorstatuslist = new ArrayList<>();
		if(status.equalsIgnoreCase("null")) {
			List<Company> companylist = companyRepo.findAll();
			if(companylist.isEmpty()) {
				return "company-list"; 
			}else {
				for(Company company:companylist) {
					if(company.getSector().equalsIgnoreCase(sector)) {
						sectorstatuslist.add(company);
					}
				}
			}
		}else{
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
		}
		model.addAttribute("companies", sectorstatuslist);
		System.out.println(sectorstatuslist.size());
		return "company-list";
	}

    @GetMapping("company/overview")
    public String getCompanyOverview(Model model) {
        User user = usercontroller.getCurrentUser();
        Company company = user.getCompany();
        model.addAttribute("bod", company);
        return "company-overview";
    }

    @PostMapping("company/overview")
    public String setCompanyOverview(Model model,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            CompanyArticles article) {
        User user = usercontroller.getCurrentUser();
        article.setTitle(title);
        article.setFull_text(description);
        article.setAuthor(user);
        article.setCompany(user.getCompany());
        articlesRepo.save(article);

        return "company-overview";
    }
}
