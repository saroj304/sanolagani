package com.bitflip.sanolagani.repository;
import com.bitflip.sanolagani.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitflip.sanolagani.models.BoardMembers;

import java.util.Set;

@Repository
public interface BoardMembersRepo extends JpaRepository<BoardMembers, Integer>{
    Set<BoardMembers> findByCompanies(Company company);
}
