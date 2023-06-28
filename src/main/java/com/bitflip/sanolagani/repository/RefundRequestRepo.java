package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.RefundRequestData;

public interface RefundRequestRepo extends JpaRepository<RefundRequestData, Integer> {

}
