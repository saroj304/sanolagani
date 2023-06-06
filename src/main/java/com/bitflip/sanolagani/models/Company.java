package com.bitflip.sanolagani.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "startup")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	private String websiteurl;
	@Column
	private String previouslyraisedcapital;
	@Column
	private String executionteamlocation;
	@Column
	private String timespanforraisingcapital;
	@Column
	private String filename;

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

	public String getExecutionteamlocation() {
		return executionteamlocation;
	}

	public void setExecutionteamlocation(String executionteamlocation) {
		this.executionteamlocation = executionteamlocation;
	}

	public String getTimespanforraisingcapital() {
		return timespanforraisingcapital;
	}

	public void setTimespanforraisingcapital(String timespanforraisingcapital) {
		this.timespanforraisingcapital = timespanforraisingcapital;
	}

}