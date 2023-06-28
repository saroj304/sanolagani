package com.bitflip.sanolagani.service;

import com.bitflip.sanolagani.models.Collateral;
import com.bitflip.sanolagani.models.Investment;
import com.bitflip.sanolagani.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface PaymentService {
    public void savePaymentDetails();
    public void getPaymentDetails();
    public void deletePaymentDetails();
    public void updatePaymentDetails();
    public boolean verifyPayment(Map<String, String> paymentDetails) throws JsonProcessingException;
	public void saveTransactionDetails(String token, String amount, int companyid,
			                          Transaction transaction,String remarks,
			                          Investment investment,Collateral collateral);
}
