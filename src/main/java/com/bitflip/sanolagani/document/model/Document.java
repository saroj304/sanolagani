package com.bitflip.sanolagani.document.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "document")
@Entity
public class Document {
    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private long fileId;

    @Column(name = "path", unique = true, nullable = false, updatable = true)
    private String filePath;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "upload_date")
    @CreationTimestamp
    private LocalDateTime uploadDate;
}
