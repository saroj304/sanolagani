package com.bitflip.sanolagani.service;

import java.util.List;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;

public interface AdminService {
	public void saveUnverifiedCompany(UnverifiedCompanyDetails company);
	public List<UnverifiedCompanyDetails> fetchAll();
	public void deleteData(int id);
	public void saveVerifiedCompany(int id,Company company);
	
   
}
