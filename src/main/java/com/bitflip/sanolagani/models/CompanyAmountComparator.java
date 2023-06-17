package com.bitflip.sanolagani.models;

import java.util.Comparator;

public class CompanyAmountComparator implements Comparator<Company>{

	@Override
	public int compare(Company o1, Company o2) {
		// Converting into double since  amount is in string
		Double c1=Double.parseDouble (o1.getPreviouslyraisedcapital());
		Double c2=Double.parseDouble (o2.getPreviouslyraisedcapital());

		return Double.compare(c2, c1);
	}

}
