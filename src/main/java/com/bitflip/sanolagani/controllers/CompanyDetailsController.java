package com.bitflip.sanolagani.controllers;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.CompanyDetailedDescription;
import com.bitflip.sanolagani.models.Notification;
import com.bitflip.sanolagani.models.TrafficData;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyDetailedDescriptionRepo;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.InvestmentRepo;
import com.bitflip.sanolagani.repository.NotificationRepo;
import com.bitflip.sanolagani.repository.TrafficDataRepo;
import com.bitflip.sanolagani.repository.UserRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    CompanyDetailedDescriptionRepo companyDetailedDescriptionRepo;

    @GetMapping("/company")
    public String getAllCompany(Model model) {
        List<Integer> companyId = companyRepo.getAllCompany();
        List<Company> companies = new ArrayList<>();
        for (int id : companyId)
            companies.add(companyRepo.getReferenceById(id));

        model.addAttribute("companies", companies);
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
        Company company = companyRepo.getReferenceById(id);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime created_date = company.getCreated();
        String time = company.getTimespanforraisingcapital();
        String[] timespansplit = time.split(" ", 2);
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
        model.addAttribute("company", company);
        return "details";
    }

    // company dashboard

    @GetMapping("/companydashboard")
    public String gerDashboard(Model model) {
        User user = usercontroller.getCurrentUser();
        int id = user.getCompany().getId();
        LocalDateTime currentDate = LocalDateTime.now();
        Map<String, Integer> pastSixdays = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");

        for (int i = 6; i >= 0; i--) {
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
        model.addAttribute("labels", labels);
        model.addAttribute("trafficvalues", trafficvalues);
        
        return "companydashboard";
    }

    @GetMapping("/notification")
    public String getNotification(Model model) {
        User user = usercontroller.getCurrentUser();
        int companyid = user.getCompany().getId();
        List<Notification> notificationlist = notificationrepo.findAllByCompanyid(companyid);
        model.addAttribute("notificationlist", notificationlist);
        return "notification";
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
                                    CompanyDetailedDescription companyDetailedDescription) {
		User user = usercontroller.getCurrentUser();
        companyDetailedDescription.setTitle(title);
        companyDetailedDescription.setFull_text(description);
        companyDetailedDescription.setAuthor(user.getId());
        companyDetailedDescriptionRepo.save(companyDetailedDescription);

		return "company-overview";
	}
}
