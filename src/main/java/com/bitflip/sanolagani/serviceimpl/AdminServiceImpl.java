package com.bitflip.sanolagani.serviceimpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;
import com.bitflip.sanolagani.repository.UnverifiedCompanyRepo;
import com.bitflip.sanolagani.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	UnverifiedCompanyRepo unverified_repo;
	UnverifiedCompanyDetails unverified_details = new UnverifiedCompanyDetails();
	@Override
	public void saveUnverifiedCompany(Company company) { 
		
		unverified_details.setCompanyname(company.getCompanyname());
		unverified_details.setPhnum(company.getPhnum());
		unverified_details.setEmail(company.getEmail());
		unverified_details.setFname(company.getFname());
		unverified_details.setLname(company.getLname());
		unverified_details.setSector(company.getSector());
		unverified_details.setWebsiteurl(company.getWebsiteurl());
		unverified_details.setCitizenship_bname(company.getCitizenship_bname());
		unverified_details.setCitizenship_fname(company.getCitizenship_fname());
		unverified_details.setPan_image_name(company.getPan_image_name());
		unverified_details.setFilename(company.getFilename());
		unverified_details.setPrice_per_share(company.getPrice_per_share());
		unverified_details.setTimespanforraisingcapital(company.getTimespanforraisingcapital());
		unverified_details.setRaisedcapital(company.getPreviouslyraisedcapital());
		unverified_repo.save(unverified_details);
	}

	@Override
	public List<UnverifiedCompanyDetails> fetchAll() {
		List<UnverifiedCompanyDetails> details = unverified_repo.findAll();
		return details;
	}

}
