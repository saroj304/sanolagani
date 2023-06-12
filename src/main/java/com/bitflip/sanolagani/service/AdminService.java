package com.bitflip.sanolagani.service;

import java.util.List;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;

public interface AdminService {
	public void saveUnverifiedCompany(Company company);
	public List<UnverifiedCompanyDetails> fetchAll();
   
}
