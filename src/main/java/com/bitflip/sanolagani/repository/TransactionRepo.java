package com.bitflip.sanolagani.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitflip.sanolagani.models.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
	@Query("SELECT t FROM Transaction t WHERE t.transaction_date_time BETWEEN :startDate AND :endDate ")
    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

	@Query("SELECT COUNT(t) FROM Transaction t")
    Long getTotalTransaction();

}
