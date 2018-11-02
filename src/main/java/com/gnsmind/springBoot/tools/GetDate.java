package com.gnsmind.springBoot.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GetDate {
	
	// yyyy-MM-dd HH:mm:ss
	public static Date getDateFormatted() throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar();
		
		//int month      = calendar.get(Calendar.MONTH) + 1; // Jan = 0, dec = 11
		//int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

		/*
		System.out.println("month \t\t: " + month);
		System.out.println("dayOfMonth \t: " + dayOfMonth);
		*/
		
		Date dateFromString = sdf.parse(sdf.format(calendar.getTime()));
		System.out.println(sdf.format(calendar.getTime()));
		
		return dateFromString;
	}
}
