package com.bitflip.sanolagani.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.CompanyArticles;
import org.springframework.stereotype.Repository;
@Repository
public interface CompanyArticlesRepo extends JpaRepository<CompanyArticles, Long>{

    public List<CompanyArticles> findByCompanyId(int company_id);
}
