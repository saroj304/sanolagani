package com.bitflip.sanolagani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.Investment;

public interface InvestmentRepo extends JpaRepository<Investment, Integer> {

@Query(value = "SELECT company_id,SUM(quantity),SUM(amount) AS total_shares\n"
		+ "FROM investment\n"
		+"where user_id =:id\n"
		+ "GROUP BY company_id\n"
		+" ",nativeQuery = true)
List<Object[]> getTotalSharesPerCompany(@Param("id")int id);

@Query("SELECT COALESCE(SUM(i.quantity),0) FROM Investment i WHERE i.company.id = :companyId AND i.user.id = :userId")
Integer getTotalQuantityByUserAndCompany(@Param("userId") int userId, @Param("companyId") int companyId);

@Query("SELECT COALESCE(SUM(i.amount),0) FROM Investment i WHERE i.user.id = :userId")
Integer getTotalInvestedAmount(@Param("userId") int userId);

}
