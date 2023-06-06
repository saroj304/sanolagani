package com.bitflip.sanolagani.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name = "user_tbl")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	@NotBlank
	private String fname;
	@Column
	@NotBlank
	private String lname;
	@Email(message = "please insert valid email address")
	@NotBlank
	private String email;
	@Column
	private String role;

	@Column
	@NotBlank
	private String password;
	
	public User() {
		this.role = "USER";
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

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}
	public String getRole() {
		return role;
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

}
