package com.bitflip.sanolagani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.TrafficData;

public interface TrafficDataRepo extends JpaRepository<TrafficData, Integer> {

	List<TrafficData> findAllByCompanyid(int companyId);
}
