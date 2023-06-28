package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitflip.sanolagani.models.Company;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
	@Query(value = "SELECT companyname FROM company WHERE id =:obj ",nativeQuery = true)
	public String getCompanyName(Object obj);

	@Query(value = "SELECT id FROM company",nativeQuery = true)
	public List<Integer> getAllCompany();
}
