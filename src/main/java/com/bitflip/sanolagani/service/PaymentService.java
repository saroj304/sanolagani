package com.bitflip.sanolagani.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface PaymentService {
    public void savePaymentDetails();
    public void getPaymentDetails();
    public void deletePaymentDetails();
    public void updatePaymentDetails();
    public void verifyPayment(Map<String, String> paymentDetails,String token,String amount) throws JsonProcessingException;
}
