package com.bitflip.sanolagani.controllers;


import com.bitflip.sanolagani.models.Collateral;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.Transaction;
import com.bitflip.sanolagani.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/verifypayment")
    public String verifyPayment(@RequestParam("token") String token, 
    		                    @RequestParam("amount") String amount,
    		                    @RequestParam(value = "companyid") int companyid,
    		                    @RequestParam(value="remarks" ,required = false) String remarks,
    		                    Transaction transaction,Investment investment,Collateral collateral) 
    		                    throws JsonProcessingException {
        System.out.println(token + " " + amount+" "+companyid);
        Map<String, String>  details = new HashMap<>();
        details.put("token", token);
        details.put("amount", amount);
       boolean result = paymentService.verifyPayment(details);
       System.out.println(result);
       if(result) {
    	   paymentService.saveTransactionDetails(token,amount,companyid,transaction,remarks,investment,collateral);
       }
        return "redirect:/home";
    }
}
