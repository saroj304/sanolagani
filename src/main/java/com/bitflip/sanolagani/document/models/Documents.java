package com.bitflip.sanolagani.document.models;

import com.bitflip.sanolagani.models.Company;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "path")
    private String filePath;

    @Column(name = "upload_on")
    private LocalDateTime uploadOn;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Documents(String filePath, Company company) {
        this.filePath = filePath;
        this.uploadOn = LocalDateTime.now();
        this.company = company;
    }
    public UUID getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadOn() {
        return uploadOn;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
