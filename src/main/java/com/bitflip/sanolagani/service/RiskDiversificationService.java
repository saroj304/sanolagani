package com.bitflip.sanolagani.service;

import java.util.List;

import com.bitflip.sanolagani.models.Company;

public interface RiskDiversificationService {
public List<Company> recommendCompanies(List<Company> previousInvestments);
public  List<Company> getPreviousInvestmentsForUser();
}
