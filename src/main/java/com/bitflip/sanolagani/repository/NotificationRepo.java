package com.bitflip.sanolagani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Integer>{
    
	public List<Notification> findAllByCompanyid(int companyid);
	
	public int countByCompanyidAndIsreadFalse(int companyid);
}
