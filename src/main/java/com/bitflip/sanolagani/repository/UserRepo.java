package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitflip.sanolagani.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	 @Query("SELECT COUNT(u) FROM User u where role='USER'")
	    Long getTotalUsers();
	 
	public User findByEmail(String email);
}
