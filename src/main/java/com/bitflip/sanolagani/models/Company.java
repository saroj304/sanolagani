package com.bitflip.sanolagani.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private String fname;
	@Column
	private String lname;
	@Column
	private String companyname;
	@Column
	private String email;
	@Column
	private String phnum;
	@Column
	private String sector;
	@Column
	private String websiteurl;
	@Column
	private String previouslyraisedcapital;
	@Column
	private double price_per_share;
	@Column
	private int minimum_quantity;
	@Column
	private String timespanforraisingcapital;
	@Column
	private String filename;
	@OneToOne(mappedBy = "company")
    private Portfolio portfolio;
	
	public Company() {
		this.minimum_quantity=1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMinimumQuantity() {
		return minimum_quantity;
	}
	public String getSector() {
		return sector;
	}
	public double getPrice_per_share() {
		return price_per_share;
	}

	public void setPrice_per_share(double price_per_share) {
		this.price_per_share = price_per_share;
	}

	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}
	@OneToMany(mappedBy = "company")
    private List<Investment> investments;


	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhnum() {
		return phnum;
	}

	public void setPhnum(String phnum) {
		this.phnum = phnum;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getWebsiteurl() {
		return websiteurl;
	}

	public void setWebsiteurl(String websiteurl) {
		this.websiteurl = websiteurl;
	}

	public String getPreviouslyraisedcapital() {
		return previouslyraisedcapital;
	}

	public void setPreviouslyraisedcapital(String previouslyraisedcapital) {
		this.previouslyraisedcapital = previouslyraisedcapital;
	}

	public String getTimespanforraisingcapital() {
		return timespanforraisingcapital;
	}

	public void setTimespanforraisingcapital(String timespanforraisingcapital) {
		this.timespanforraisingcapital = timespanforraisingcapital;
	}

}