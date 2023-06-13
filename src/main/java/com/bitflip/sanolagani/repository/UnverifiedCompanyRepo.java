package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bitflip.sanolagani.models.UnverifiedCompanyDetails;

public interface UnverifiedCompanyRepo extends JpaRepository<UnverifiedCompanyDetails, Integer>  {

}
