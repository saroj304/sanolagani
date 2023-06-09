package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitflip.sanolagani.models.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	public Role findByName(String name);
}
