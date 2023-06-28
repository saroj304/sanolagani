package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

}
