package com.project.test.organeHRM.pages.seleniumUIpages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.project.test.organeHRM.drivers.DriverManager;
import com.project.test.organeHRM.utils.SeleniumWebActions;

public class HomePage extends SeleniumWebActions {

	private WebDriver driver;
	private WebDriverWait wait;

	public HomePage() {
		this.driver = DriverManager.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		PageFactory.initElements(driver, this);
	}

	// Page Elements

	@FindBy(css = ".site-options")
	private WebElement siteDropdownSelectBox;

	@FindBy(css = ".dropdown-content div")
	private List<WebElement> getAllSiteDropdownValues;
	
	@FindBy(css=".area-text")
	private WebElement getSelectedSite;

	// Page Actions
	public void clicksiteDropdownSelectBox() {
		wait.until(ExpectedConditions.elementToBeClickable(siteDropdownSelectBox)).click();
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public void selectSiteFromDropdown(String siteName) {
		clicksiteDropdownSelectBox();
		wait.until(d -> {
			// Refresh elements to avoid staleness
			return !getAllSiteDropdownValues.isEmpty() && getAllSiteDropdownValues.get(0).isDisplayed();
		});

		getAllSiteDropdownValues.stream().filter(option -> option.getText().equalsIgnoreCase(siteName)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("Site '" + siteName + "' not found in dropdown")).click();
	}

	public void logout() {
		// TODO Auto-generated method stub

	}

	public void verifyDashboardLoaded() {
		// TODO Auto-generated method stub
		Assert.assertEquals(getPageTitle(), "Performance+");
		System.out.println("Browser Loaded");

	}

	public Object isWelcomeMessageDisplayed() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDashboardLoaded() {
		// TODO Auto-generated method stub
		return getPageTitle().contains("getPageTitle");
	}
	
	public void verifySiteSelected(String siteName) {
		Assert.assertEquals(getSelectedSite.getText(), siteName);
	}
}