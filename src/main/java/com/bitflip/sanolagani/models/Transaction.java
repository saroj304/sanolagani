package com.bitflip.sanolagani.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
    @JoinColumn(name = "investment_id")
    private Investment investment;
	@Column
	private String stock_type;
	@Column
	private int quantity;
	@Column
	private double price;
	@Column
	private LocalDateTime transaction_date_time;
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
	public String getStock_type() {
		return stock_type;
	}
	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public LocalDateTime getTransaction_date_time() {
		return transaction_date_time;
	}
	public void setTransaction_date_time(LocalDateTime transaction_date_time) {
		this.transaction_date_time = transaction_date_time;
	}
	
}
