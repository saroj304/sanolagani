package com.bitflip.sanolagani.document.repository;

import com.bitflip.sanolagani.document.models.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Documents, UUID> {
    @Query(value = "SELECT id FROM company WHERE email =:email ",nativeQuery = true)
    public int getCompanyId(String email);

}
