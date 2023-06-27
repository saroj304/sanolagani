package com.bitflip.sanolagani.models;

import java.util.Comparator;

public class CompanyAmountComparator implements Comparator<Company>{
	
	@Override
	public int compare(Company o1, Company o2) {
		// Converting into double since  amount is in string
   		Double c1=Double.parseDouble (o1.getPreviouslyraisedcapital());
		Integer a1=Integer.parseInt(o1.getPreviouslyraisedcapital());
//		System.out.println(c1);
		
		Double c2=Double.parseDouble (o2.getPreviouslyraisedcapital());
		Integer a2=Integer.parseInt(o2.getPreviouslyraisedcapital());
//        System.out.println(c2);
		return Double.compare(c2, c1);
	}
}
