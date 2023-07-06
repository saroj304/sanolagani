package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.CompanyDetailedDescription;

public interface CompanyDetailedDescriptionRepo extends JpaRepository<CompanyDetailedDescription, Long>{
    
}
