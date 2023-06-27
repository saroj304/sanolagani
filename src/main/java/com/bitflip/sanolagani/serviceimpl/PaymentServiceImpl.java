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
  @Autowired
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
    public void verifyPayment(Map<String, String> paymentDetails) throws JsonProcessingException {
        final String uri = "https://khalti.com/api/v2/payment/verify/";
        RestTemplate restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.set("Authorization", "Key test_secret_key_4e85bb4e70114300844afa1e59abfb00");

        String json = new ObjectMapper().writeValueAsString(paymentDetails);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
//        URI response = restTemplate.postForLocation(uri, entity);
        ResponseEntity resp = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);


    }
}
