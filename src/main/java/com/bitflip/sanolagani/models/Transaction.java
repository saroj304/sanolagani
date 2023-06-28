package com.bitflip.sanolagani.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne(mappedBy = "transaction")
    private Investment investment;
	
	@OneToOne(mappedBy = "transaction")
    private Collateral collateral;
	@Column
	private String payment_token;
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	@Column
	private double amount;
	@Column
	private LocalDateTime transaction_date_time;
	@Column
	private String transaction_type;
	@OneToOne(mappedBy = "transaction")
    private RefundRequestData refundrequest;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Investment getInvestment() {
		return investment;
	}
	public void setInvestment(Investment investment) {
		this.investment = investment;
	}

	
	public LocalDateTime getTransaction_date_time() {
		return transaction_date_time;
	}
	public void setTransaction_date_time(LocalDateTime transaction_date_time) {
		this.transaction_date_time = transaction_date_time;
	}
	public String getPayment_token() {
		return payment_token;
	}
	public void setPayment_token(String payment_token) {
		this.payment_token = payment_token;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Collateral getCollateral() {
		return collateral;
	}
	public void setCollateral(Collateral collateral) {
		this.collateral = collateral;
	}
	public RefundRequestData getRefundrequest() {
		return refundrequest;
	}
	public void setRefundrequest(RefundRequestData refundrequest) {
		this.refundrequest = refundrequest;
	}
	
}
