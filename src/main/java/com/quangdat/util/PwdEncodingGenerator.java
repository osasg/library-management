package com.quangdat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.gson.Gson;
import com.quangdat.entities.Shelf;

public class PwdEncodingGenerator {

	public static void main(String[] args) {
		String password = "dat";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPwd = encoder.encode(password);
		System.out.println(encodedPwd);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
		try {
			date1 = sdf.parse("2009-12-31");
			Date date2 = sdf.parse("2010-01-31");

	        System.out.println("date1 : " + sdf.format(date1));
	        System.out.println("date2 : " + sdf.format(date2));

	        if (date1.compareTo(date2) > 0) {
	            System.out.println("Date1 is after Date2");
	        } else if (date1.compareTo(date2) < 0) {
	            System.out.println("Date1 is before Date2");
	        } else if (date1.compareTo(date2) == 0) {
	            System.out.println("Date1 is equal to Date2");
	        } else {
	            System.out.println("How to get here?");
	        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Shelf> shelves = new ArrayList<>();
		shelves.add(new Shelf("sdsd", 1L, "dsds", null, null));
		shelves.add(new Shelf("ssssssdsd", 2L, "dsddsdss", null, null));
		Gson gson = new Gson();
		String json = gson.toJson(shelves);
		System.out.println(json);
		
	}
		
}
