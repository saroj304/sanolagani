package com.bitflip.sanolagani.serviceimpl;

import com.bitflip.sanolagani.controllers.UserController;
import com.bitflip.sanolagani.models.Collateral;
import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.Transaction;
import com.bitflip.sanolagani.models.User;
import com.bitflip.sanolagani.repository.CollateralRepo;
import com.bitflip.sanolagani.repository.CompanyRepo;
import com.bitflip.sanolagani.repository.InvestmentRepo;
import com.bitflip.sanolagani.repository.TransactionRepo;
import com.bitflip.sanolagani.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private HttpHeaders headers;
    @Autowired
    UserController user_controller;
    @Autowired
    CompanyRepo company_repo;
    @Autowired
    InvestmentRepo invest_repo;
    @Autowired
    TransactionRepo trans_repo;

    @Autowired
    CollateralRepo collateral_repo;
    @Override
    public void savePaymentDetails() {

    }

    @Override
    public void getPaymentDetails() {

    }

    @Override
    public void deletePaymentDetails() {

    }

    @Override
    public void updatePaymentDetails() {

    }

    @Override
    @CrossOrigin
    public boolean verifyPayment(Map<String, String> paymentDetails,String token,String amount) throws JsonProcessingException {

        final String uri = "https://khalti.com/api/v2/payment/verify/";
        RestTemplate restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.set("Authorization", "Key test_secret_key_1bc9f938dc3e46369f019527b8acb15b");

        String json = new ObjectMapper().writeValueAsString(paymentDetails);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity resp = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        int status = resp.getStatusCodeValue();
        System.out.println(resp.getBody());
        if(status==200) {
//        	saveInvestmentdata(token,amount);
            return true;
        }
        return false;
    }


	@Override
	public void saveTransactionDetails(String token, String amounts,
										int companyid, Transaction transaction,
										String remarks,Investment investment,Collateral collateral) {
		
		User user = user_controller.getCurrentUser();
		LocalDateTime dateTime = LocalDateTime.now();
		double amount = Double.parseDouble(amounts);
		transaction.setAmount(amount);
        transaction.setPayment_token(token);
        transaction.setTransaction_date_time(dateTime);
        transaction.setUser(user);
        if(companyid!=0) {
           transaction.setTransaction_type("investment");
           trans_repo.save(transaction);
           saveInvestmentDetails(token,amount,companyid,transaction,remarks,user,investment);
        }else {
        	transaction.setTransaction_type("collateral");
        	 trans_repo.save(transaction);
        	 saveCollateralDetails(user,amount,remarks,transaction,collateral);
        }
       
		
	}
    
  

	
	public void saveInvestmentDetails(String token, double amounts, 
			                           int companyid,Transaction transaction,
			                           String remarks,User user,Investment investment) {
		String remark=remarks;
		Company company = company_repo.getReferenceById(companyid);
	
			LocalDateTime dateTime = LocalDateTime.now();
			double pricepershare = company.getPrice_per_share();
			int quantity = (int) (amounts/pricepershare);
			investment.setAmount(amounts);
			investment.setQuantity(quantity);
			investment.setUser(user);
			investment.setCompany(company);
			investment.setStatus("refundable");
			investment.setInvestment_date_time(dateTime);
			investment.setTransaction(transaction);
			invest_repo.save(investment);
			System.out.println("investment save :"+investment);
			
			
	}
	
	
	public void saveCollateralDetails(User user, double amount, String remarks,
										Transaction transaction,Collateral collateral) {
		System.out.println("in collateral");
		List <Collateral> collaterallist = collateral_repo.findAll();
		System.out.println(collaterallist);
		if(collaterallist.isEmpty()) {
			System.out.println("inside null");
			 collateral.setUser(user);
	         collateral.setCollateral_amount(amount);
	         collateral.setRemarks(remarks);
	         collateral.setTransaction(transaction);
	         collateral_repo.save(collateral);
		}else {
		      for(Collateral collaterals:collaterallist) {
			     if (collaterals.getUser().getId()==user.getId()) {
			    	 System.out.println("user equal");
			        double previous_amount = collaterals.getCollateral_amount();
			        previous_amount+=amount;
			        collaterals.setCollateral_amount(previous_amount);
			        collaterals.setRemarks(remarks);
			        collateral_repo.save(collaterals);
			       break;
		           }else {
		        	   System.out.println("user not equal");
		        	 collateral.setUser(user);
		  	         collateral.setCollateral_amount(amount);
		  	         collateral.setRemarks(remarks);
			         collateral.setTransaction(transaction);
		  	         collateral_repo.save(collateral);
		  	         
		           }
	}
		}
	}}


