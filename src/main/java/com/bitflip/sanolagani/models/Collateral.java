package com.bitflip.sanolagani.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "portfolio")
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
    @JoinColumn(name = "user_id")
    private User user;
	@OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
