package com.bitflip.sanolagani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitflip.sanolagani.models.Company;
@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {
	@Query(value = "SELECT companyname FROM company WHERE id =:obj ",nativeQuery = true)
	public String getCompanyName(Object obj);
    @Query(value = "SELECT * FROM company ORDER BY previouslyraisedcapital desc", nativeQuery = true)
	public List<Company> findAllCompanyBasesOnRaidedCapitalDesc();
    @Query(value = "SELECT * FROM company ORDER BY created desc", nativeQuery = true)
	public List<Company> findAllCompanyBasesOnCreationalDates();
    @Query("SELECT COUNT(u) FROM User u where role='COMPANY'")
    Long getTotalcompany();
}
