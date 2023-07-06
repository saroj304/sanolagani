package com.bitflip.sanolagani.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "company_details_markup")
public class CompanyDetailedDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(updatable = false)
    private LocalDateTime authored_date_time;

    private LocalDateTime last_modified_date_time;

    @Column(nullable = false)
    private Integer author;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 100000)
    private String full_text;

    public CompanyDetailedDescription(){
        this.authored_date_time = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getAuthor() {
        return author;
    }

    public LocalDateTime getAuthored_date_time() {
        return authored_date_time;
    }

    public LocalDateTime getLast_modified_date_time() {
        return last_modified_date_time;
    }

    public void setLast_modified_date_time(LocalDateTime last_modified_date_time) {
        this.last_modified_date_time = last_modified_date_time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.last_modified_date_time = LocalDateTime.now();
        this.title = title;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }
}
