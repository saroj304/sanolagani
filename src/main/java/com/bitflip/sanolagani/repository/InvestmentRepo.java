package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.Investment;

public interface InvestmentRepo extends JpaRepository<Investment, Integer> {

}
