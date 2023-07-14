package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitflip.sanolagani.models.CompanyFile;
@Repository
public interface CompanyFileRepo extends JpaRepository<CompanyFile, Integer>{

}
