package com.bitflip.sanolagani.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name = "collateral")
public class Collateral {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private double collateral_amount;
	@Column
	private String remarks;
	@OneToOne
    @JoinColumn(name = "user_id")
    private User user;
	@OneToOne
	@JoinColumn(name = "transaction_id")
	private Transaction transaction;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getCollateral_amount() {
		return collateral_amount;
	}
	public void setCollateral_amount(double collateral_amount) {
		this.collateral_amount = collateral_amount;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Transaction getTransaction() {
		return transaction;
	}
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	

}
