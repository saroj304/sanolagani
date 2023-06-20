package com.bitflip.sanolagani.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ManyToAny;
import org.springframework.stereotype.Component;

@Component

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private String fname;
	@Column
	private String lname;
	@Email(message = "please insert valid email address")
	private String email;
	@Column
	private String password;
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//	@JoinTable(name = "user_role_tbl", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
//			@JoinColumn(name = "role_id") })
//	private Set<Role> role = new HashSet<>();
	@OneToOne(mappedBy = "user")
	private Company company;

    @OneToMany(mappedBy = "user")
	private List<Investment> investments;
    
    @OneToMany(mappedBy = "user")
	private List<Feedback> feedbacklist;
	
	@OneToOne(mappedBy = "user")
    private Portfolio portfolio;
	@Column
	private String role;


	public User() {
		this.role = "USER";
	}

	public String getRole() {
		return role;
	}
	
	
	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}

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

	public void setRole(String role) {
		this.role = role;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Feedback> getFeedbacklist() {
		return feedbacklist;
	}

	public void setFeedbacklist(List<Feedback> feedbacklist) {
		this.feedbacklist = feedbacklist;
	}

}
