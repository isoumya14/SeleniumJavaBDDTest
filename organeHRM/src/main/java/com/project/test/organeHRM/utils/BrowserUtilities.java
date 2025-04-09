package com.project.test.organeHRM.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class BrowserUtilities {
	
	WebDriver driver;
	
	// Get current page title
	public String getPageTitle() {
	    return driver.getTitle();
	}

	// Refresh page
	public void refreshPage() {
	    driver.navigate().refresh();
	}
	
	// Add a cookie
	public void addCookie(String name, String value) {
	    driver.manage().addCookie(new Cookie(name, value));
	}

	// Get cookie by name
	public String getCookieValue(String name) {
	    return driver.manage().getCookieNamed(name).getValue();
	}

}
