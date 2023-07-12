package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.BoardMembers;
import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.CompanyArticles;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.Notification;
import com.bitflip.sanolagani.models.TrafficData;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.models.Watchlist;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.LinkedHashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitflip.sanolagani.controllers.AdminController;
import org.springframework.web.bind.annotation.RequestBody;


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
    HomeController homecontroller;

    @Autowired
    private SentimentPreprocessor pre;
    @Autowired
    RecommendationInitializer recommedationinit;

    @Autowired
    AdminController admin_controller;
    
    @Autowired
    WatchlistRepo watchlistrepo;

    @Autowired
    BoardMembersRepo boardMembersRepo;

    @GetMapping("/company")
    public String getAllCompany(Model model) {
        List<Company> companylist = companyRepo.findAll();
        if (companylist.isEmpty()) {
            return "company-list";
        }
        Map<String, Integer> totalUsersInvestedMap = new HashMap<>();
		Map<String,Integer> remainingdaysmap = new HashMap<>();
		Map<String, Integer> totalApplyShareMap = new HashMap<>();
        Set<String> sectorlist = new HashSet<>();

        for(Company company:companylist) {
			totalUsersInvestedMap.put(company.getCompanyname(), admin_controller.getTotalNumberOfUserInvested(company));
            remainingdaysmap.put(company.getCompanyname(), homecontroller.calculateRemainingDays(company));
			totalApplyShareMap.put(company.getCompanyname(),admin_controller.getTotalNumberOfShareApplied(company));

            sectorlist.add(company.getSector());
        }
        
        model.addAttribute("sectorlist", sectorlist);
        model.addAttribute("totalUsersInvestedMap", totalUsersInvestedMap);
        model.addAttribute("remainingdaysmap", remainingdaysmap);
        model.addAttribute("totalApplyShareMap", totalApplyShareMap);
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
        } else if (isMatch && user.getRole().equalsIgnoreCase("ADMIN")) {
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
       boolean is_invested = true;
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
         if(numberofshare_peruser==0) {
        	 is_invested=false;
            }
         if (company.getMaximum_quantity() <= numberofshare_peruser) {
            status = "limit reached";
        } else if (now.isAfter(created_date.plusDays(timespan))) {
            status = "time finish";

        }

        Set<BoardMembers> boardMembers = boardMembersRepo.findByCompanies(company);

        
        List<Watchlist> watchlist = watchlistrepo.findByUserId(user.getId());
        List<Integer> companyids = new ArrayList<>();
        boolean iswatchlistedblank= true;
        if(!watchlist.isEmpty()) {
        	iswatchlistedblank=false;
        	for(Watchlist watch:watchlist) {
        		if(watch.getCompany_id()==id) {
        			companyids.add(watch.getCompany_id());
        		}
        	}
        	model.addAttribute("companyids", companyids);
        }
        model.addAttribute("is_invested", is_invested);
        model.addAttribute("iswatchlistedblank", iswatchlistedblank);
        model.addAttribute("status", status);
        model.addAttribute("company", company);
        model.addAttribute("boardMembers", boardMembers);
        System.out.println(boardMembers);
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

    @GetMapping("/company/companydashboard")
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

    @GetMapping("/company/notification")
    public String getNotification(Model model) {
        User user = usercontroller.getCurrentUser();
        int companyid = user.getCompany().getId();
        List<Notification> notificationlist = notificationrepo.findAllByCompanyid(companyid);
        markNotificationAsRead(notificationlist);
        Collections.reverse(notificationlist);
        
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

    @GetMapping("/company/filter/{sector}")
    public String getCompanyBySector(@PathVariable(value = "sector",required=false) String sector,
    		                                     Model model) {
	        
		List<Company> sectorstatuslist = new ArrayList<>();
		List<Company> companylist = companyRepo.findAll();
		System.out.println(companylist.size());
			if(companylist.isEmpty()) {
				return "company-list"; 
			}else {
				for(Company company:companylist) {
					if(company.getSector().equalsIgnoreCase(sector)) {
						sectorstatuslist.add(company);
					}
				}
			}
	
		Map<String, Integer> totalUsersInvestedMap = new HashMap<>();
		Map<String,Integer> remainingdaysmap = new HashMap<>();
		Map<String, Integer> totalApplyShareMap = new HashMap<>();
        Set<String> sectorlist = new HashSet<>();

		 for(Company company:companylist) {
				totalUsersInvestedMap.put(company.getCompanyname(), admin_controller.getTotalNumberOfUserInvested(company));
	            remainingdaysmap.put(company.getCompanyname(), homecontroller.calculateRemainingDays(company));
				totalApplyShareMap.put(company.getCompanyname(),admin_controller.getTotalNumberOfShareApplied(company));
		        sectorlist.add(company.getSector());
	        }
	        model.addAttribute("sectorlist", sectorlist);
	        model.addAttribute("totalUsersInvestedMap", totalUsersInvestedMap);
	        model.addAttribute("remainingdaysmap", remainingdaysmap);
	        model.addAttribute("totalApplyShareMap", totalApplyShareMap);
		    model.addAttribute("companies", sectorstatuslist);
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

    @GetMapping("/company/management")
    public String getCompanyManagement() {
        User user = usercontroller.getCurrentUser();
        Company company = user.getCompany();
        Set<BoardMembers> boardMembers = boardMembersRepo.findByCompanies(company);
        System.out.println(boardMembers);
        return "company-management";
    }

    @PostMapping(value="/company/management")
    public String postCompanyManagement(@RequestBody MultiValueMap<String, String> formData, 
                                        BoardMembers member) {
        Integer fields = Integer.parseInt(formData.getFirst("numberOfInputFields"));
        Set<BoardMembers> members = new HashSet<>();
        for(int i=0; i<fields; i++) {
            String firstName = formData.getFirst("firstName-"+i);
            String middleName = formData.getFirst("middleName-"+i);
            String lastName = formData.getFirst("lastName-"+i);
            String title = formData.getFirst("title-"+i);
            String socialLink = formData.getFirst("socialLink-"+i);
            member.setFirstName(firstName);
            member.setMiddleName(middleName);
            member.setLastName(lastName);
            member.setPosition(title);
            members.add(member);
            member = new BoardMembers();
        }
        if(members != null) {
            boardMembersRepo.saveAll(members);
        }
        return "company-management";
    }
    
 
    
}
