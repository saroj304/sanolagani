package com.bitflip.sanolagani.models;


import java.time.Month;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="trafficdata")
public class TrafficData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    @Column
    private int count;
    @Column
    private int companyid;
    @Column
    private String visitmonth;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
	public String getVisitmonth() {
		return visitmonth;
	}
	public void setVisitmonth(String visitmonth) {
		this.visitmonth = visitmonth;
	}
	
    
    
}
