package com.bitflip.sanolagani.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class CompanyDateComparator implements Comparator<Company>{

	@Override
	public int compare(Company o1, Company o2) {
		//convert the date into string format
//	DateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	String date1=String.valueOf(o1.getCreated());
//	if(date1.isBlank()) {
//		System.out.println("date is null");
//	}
//	System.out.println(date1);
//	String date2=String.valueOf(o2.getCreated());
//	try {
//		System.out.println(dateformat.parse(date1));
//	} catch (ParseException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//		try {
//			return dateformat.parse(date1).compareTo(dateformat.parse(date2));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		    throw new IllegalArgumentException();	
//		}
		return (o2.getCreated()).compareTo(o1.getCreated());
	
		}

}
