package com.bitflip.sanolagani.controllers;


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
    public String verifyPayment(@RequestParam("token") String token, @RequestParam("amount") String amount, @RequestParam("cId") String id, Model model) throws JsonProcessingException {
        System.out.println(token + " " + amount);
        Map<String, String>  details = new HashMap<>();
        details.put("token", token);
        details.put("amount", amount);
        boolean status = paymentService.verifyPayment(details,token,amount);
        double amountRs = Double.parseDouble(amount)/100;
        model = status ? model.addAttribute("message", "success") : model.addAttribute("message", "failed!");
        return "redirect:/company/details/"+id +"/";
    }
}
