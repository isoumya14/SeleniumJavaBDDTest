package com.project.test.organeHRM.utils;


import java.util.Random;

public class DataUtils {

	
	// Generate random email (for test registrations)
	public String generateRandomEmail() {
	    return "user" + System.currentTimeMillis() + "@mail.com";
	}

	// Generate random string (for names, etc.)
	public String generateRandomString(int length) {
	    return new Random().ints(97, 123) // ASCII a-z
	            .limit(length)
	            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	            .toString();
	}
	
	
}
