package com.project.test.organeHRM.pages.seleniumUIpages;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.project.test.organeHRM.drivers.DriverManager;
import com.project.test.organeHRM.utils.SeleniumWebActions;
import com.project.test.organeHRM.utils.waitUtil.WaitUtils;

public class LoginPage extends SeleniumWebActions{

    private WebDriver driver;
    
    public LoginPage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
        
    }
    
    
    // Page Factory elements
    @FindBy(css = ".input-box")
    private WebElement userNameInput;
    
    @FindBy(xpath="//div[contains(text(),'Next')]")
    private WebElement nextButton;

    @FindBy(css = "#password")
    private WebElement passwordInput;
    
    @FindBy(css="[data-id=submit-button]")
    private WebElement signOnBtn;
    
    @FindBy(css=".home-page-header-welcome-message")
    private WebElement getWelcomeMessage;
    
    @FindBy(css=".error-message")
    private WebElement getInvalidUserNameMsg;
    
    
    @FindBy(css=".feedback__message")
    private WebElement getInvalidPasswordMsg;
    
    @FindBy(css=".logo")
    private WebElement getLogo;


    
   // Page actions
    

    
    public boolean isPageLogoDisplayed(int timeoutSec) {
        return WaitUtils.waitForDisplayed(getLogo, DriverManager.getDriver(), timeoutSec);
    }
    
    
    
    public boolean isLoginPageDisplayed() {
        return driver.getTitle().equalsIgnoreCase("Performance+");
    }

    public void enterEmail(String email) {
        userNameInput.clear();
        userNameInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        signOnBtn.click();
    }

    
    public boolean isErrorMessageDisplayed() {
        try {
            return getInvalidPasswordMsg.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getInvalidUserNameErrorMessageText() {
        return getInvalidUserNameMsg.getText();
    }
    
    public String getInvalidPasswordErrMsg() {
    	return getInvalidPasswordMsg.getText();
    }

    // Comprehensive login method
    public void login(String email, String password) {
    	waitForClickable(userNameInput, 60);
        enterEmail(email);
        nextButton.click();
    	waitForClickable(passwordInput, 60);
        enterPassword(password);
        clickLoginButton();
    }
}