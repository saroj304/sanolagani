package com.bitflip.sanolagani.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Transaction;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.TransactionRepo;
import com.bitflip.sanolagani.repository.UserRepo;
import com.bitflip.sanolagani.service.AdminService;

@Controller
public class AdminController {
	@Autowired
	AdminService admin_service;
	@Autowired
	 private TransactionRepo transactionRepository;
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private CompanyRepo companyrepo;
	
	
	@GetMapping("/admin")
	public String getAdminPage() {
		
		return "admin";
	}
	
	@GetMapping("/admindashboard")
	public String getAdminDashboardPage(Model model) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime fiftendaysago = now.minusHours(24);
	     List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(fiftendaysago, now);
	     Map<String, Double> aggregatedData = aggregateData(transactions);

	  	     
	     List<String> keysList = new ArrayList<>(aggregatedData.keySet());
	     List<Double> valuesList = new ArrayList<>(aggregatedData.values());
	     
	     Long usercount  = userrepo.getTotalUsers();
	     Long companycount =companyrepo.getTotalcompany();
	     Long transactioncount = transactionRepository.getTotalTransaction();
	     
	     
	     model.addAttribute("usercount", usercount);
	     model.addAttribute("companycount", companycount);
	     model.addAttribute("transactioncount", transactioncount);
	     model.addAttribute("labels", keysList);
	     model.addAttribute("transactionAmounts", valuesList);

	     return "admindashboard";
	 }
    

	private static Map<String, Double> aggregateData(List<Transaction> transactions) {
        Map<String, Double> aggregatedData = new LinkedHashMap<>();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

	    LocalDateTime currentTime = LocalDateTime.now();
	    LocalDateTime startTime = currentTime.minusHours(24).withMinute(0).withSecond(0).withNano(0);

	    while (startTime.isBefore(currentTime)) {
	        final LocalDateTime currentHour = startTime; 
	        LocalDateTime endTime = startTime.plusHours(1);
	        String label = startTime.format(formatter);
	        double totalAmount = transactions.stream()
	                .filter(transaction -> transaction.getTransaction_date_time().isAfter(currentHour)
	                        && transaction.getTransaction_date_time().isBefore(endTime))
	                .mapToDouble(Transaction::getAmount)
	                .sum();

	        aggregatedData.put(label, totalAmount);

	        startTime = endTime;
	    }
	    return aggregatedData;
	}


	@GetMapping("/tables")
	public String getunverifiedcompany(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getName().equals("anonymousUser")) {
			List<UnverifiedCompanyDetails> details = admin_service.fetchAll();
			if (details != null) {
				model.addAttribute("UnverfiedCompanies", details);
				return "table";
			}
			return "table";
		} else {
			return "redirect:/home";
		}
	}

	@GetMapping("/tables/edit/{id}")
	public String deleteUnverifiedData(@PathVariable("id") String ids) {
		int id = Integer.parseInt(ids);
		admin_service.deleteData(id);
		return "redirect:/tables";
	}

	@GetMapping("/tables/edit/save/{id}")
	public String addVerifiedCompany(@PathVariable("id") String ids, Company company, User user) {
		int id = Integer.parseInt(ids);
		admin_service.saveVerifiedCompany(id, company, user);
		return "redirect:/tables";
	}
}
