package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.Company;
import com.bitflip.sanolagani.models.User;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
}
