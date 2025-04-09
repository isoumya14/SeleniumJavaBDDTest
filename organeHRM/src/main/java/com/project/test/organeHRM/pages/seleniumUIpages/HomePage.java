package com.project.test.organeHRM.pages.seleniumUIpages;

import java.time.Duration;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project.test.organeHRM.drivers.DriverManager;

public class HomePage {
	
	private WebDriver driver;
    private WebDriverWait wait;

	public HomePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
	
	  // Page Elements
    @FindBy(partialLinkText = "Products")
    private WebElement productMenuLink;

    @FindBy(css = ".add-to-cart")
    private WebElement addToCartBtn;
    
    @FindBy(partialLinkText = "View Cart")
    private WebElement viewCartBtn;
    
    // Page Actions
    public void clickProductBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(productMenuLink)).click();
    }
     
    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }
     
    public void viewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartBtn)).click();
    }

    // Additional utility methods
    public boolean isProductMenuVisible() {
        return wait.until(ExpectedConditions.visibilityOf(productMenuLink)).isDisplayed();
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
}