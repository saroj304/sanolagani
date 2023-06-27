package com.bitflip.sanolagani.serviceimpl;

import com.bitflip.sanolagani.service.PaymentService;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    private HttpHeaders headers;
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
    public void verifyPayment(Map<String, String> paymentDetails,String token,String amount) throws JsonProcessingException {
        final String uri = "https://khalti.com/api/v2/payment/verify/";
        RestTemplate restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.set("Authorization", "Key test_secret_key_1bc9f938dc3e46369f019527b8acb15b");

        String json = new ObjectMapper().writeValueAsString(paymentDetails);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity resp = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        int status = resp.getStatusCodeValue();
        if(status==200) {
//        	saveInvestmentdata(token,amount);
        }
    }

}