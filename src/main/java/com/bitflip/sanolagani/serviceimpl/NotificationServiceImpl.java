package com.bitflip.sanolagani.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitflip.sanolagani.models.Notification;
import com.bitflip.sanolagani.repository.NotificationRepo;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class NotificationServiceImpl {
   @Autowired
   NotificationRepo notificationrepo;

	    public void saveNotification(String message,Notification notification,int userid,int companyid) {
	        notification.setMessage(message);
	        notification.setCreatedAt(LocalDateTime.now());
	        notification.setUserid(userid);
	        notification.setCompanyid(companyid);
	        notification.setIsread(false);
	        notificationrepo.save(notification);
	    }

	   
	}
	






