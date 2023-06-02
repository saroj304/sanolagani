package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bitflip.sanolagani.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
}
