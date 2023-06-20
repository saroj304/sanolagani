package com.bitflip.sanolagani.models;

//import java.util.HashSet;
import java.util.List;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private String companyname;
	@Column
	private String address;
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
	@Column
	private String pan_image_name;
	@Column
	private String citizenship_fname;
	@Column
	private String citizenship_bname;
	@Column
	private String image;
 
	private String role;
	@Column
	private int maximum_quantity;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@OneToOne(mappedBy = "company")
	private Portfolio portfolio;
	
	@OneToMany(mappedBy = "company")
	private List<Investment> investments;
	
	@OneToMany(mappedBy = "company")
	private List<Feedback> feedbacklist;

	public Company() {
		this.minimum_quantity = 1;
		this.role = "COMPANY";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
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

	public int getMinimum_quantity() {
		return minimum_quantity;
	}

	public void setMinimum_quantity(int minimum_quantity) {
		this.minimum_quantity = minimum_quantity;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

	

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getPhnum() {
		return phnum;
	}

	public void setPhnum(String phnum) {
		this.phnum = phnum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getPan_image_name() {
		return pan_image_name;
	}

	public void setPan_image_name(String pan_image_name) {
		this.pan_image_name = pan_image_name;
	}

	public String getCitizenship_fname() {
		return citizenship_fname;
	}

	public void setCitizenship_fname(String citizenship_name) {
		this.citizenship_fname = citizenship_name;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public String getCitizenship_bname() {
		return citizenship_bname;
	}

	public void setCitizenship_bname(String citizenship_bname) {
		this.citizenship_bname = citizenship_bname;
	}

	public int getMaximum_quantity() {
		return maximum_quantity;
	}

	public void setMaximum_quantity(int maximum_quantity) {
		this.maximum_quantity = maximum_quantity;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public List<Feedback> getFeedbacklist() {
		return feedbacklist;
	}

	public void setFeedbacklist(List<Feedback> feedbacklist) {
		this.feedbacklist = feedbacklist;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}