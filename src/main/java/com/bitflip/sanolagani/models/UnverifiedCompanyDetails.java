package com.bitflip.sanolagani.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "unverifiedCompany")
public class UnverifiedCompanyDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	@NotBlank
	private String fname;
	@Column
	@NotBlank
	private String lname;
	@Column
	@NotBlank
	private String companyname;
	@Column
	@Email
	private String email;
	@Column
	@NotBlank
//	@Min(value=1,message="should be greater than or equal to 1")
	private String sector;
	@Column
	@NotBlank
	private String websiteurl;
	@Column
	
	private double price_per_share;
	@Column
	@NotNull
	private int maximum_quantity;
	@Column
	@NotBlank
	private String timespanforraisingcapital;
	@Column
	private String filename;
	@Column
	
	private String pan_image_name;
	@Column
	
	private String citizenship_fname;
	@Column
	
	private String phnum;
	@Column
	
	private String citizenship_bname;
	@Column
	@NotBlank
	private String raisedcapital;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public double getPrice_per_share() {
		return price_per_share;
	}

	public void setPrice_per_share(double price_per_share) {
		this.price_per_share = price_per_share;
	}

	public String getTimespanforraisingcapital() {
		return timespanforraisingcapital;
	}

	public void setTimespanforraisingcapital(String timespanforraisingcapital) {
		this.timespanforraisingcapital = timespanforraisingcapital;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPan_image_name() {
		return pan_image_name;
	}

	public void setPan_image_name(String pan_image_name) {
		this.pan_image_name = pan_image_name;
	}

	public String getCitizenship_fname() {
		return citizenship_fname;
	}

	public void setCitizenship_fname(String citizenship_fname) {
		this.citizenship_fname = citizenship_fname;
	}

	public String getCitizenship_bname() {
		return citizenship_bname;
	}

	public void setCitizenship_bname(String citizenship_bname) {
		this.citizenship_bname = citizenship_bname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getWebsiteurl() {
		return websiteurl;
	}

	public void setWebsiteurl(String websiteurl) {
		this.websiteurl = websiteurl;
	}

	public String getRaisedcapital() {
		return raisedcapital;
	}

	public void setRaisedcapital(String raisedcapital) {
		this.raisedcapital = raisedcapital;
	}

	public int getMaximum_quantity() {
		return maximum_quantity;
	}

	public void setMaximum_quantity(int maximum_quantity) {
		this.maximum_quantity = maximum_quantity;
	}

}