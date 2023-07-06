package com.bitflip.sanolagani.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bitflip.sanolagani.models.BoardMembers;

public interface BoardMembersRepo extends JpaRepository<BoardMembers, Integer>{
    
}
