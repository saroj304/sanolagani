package com.bitflip.sanolagani.document.repository;

import com.bitflip.sanolagani.document.models.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Documents, Long> {

}
