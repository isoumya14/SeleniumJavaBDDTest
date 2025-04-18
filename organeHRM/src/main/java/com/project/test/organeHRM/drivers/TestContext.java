package com.project.test.organeHRM.drivers;

import org.openqa.selenium.WebDriver;
import com.project.test.organeHRM.pages.seleniumUIpages.*;
import com.project.test.organeHRM.utils.FileActions;
import com.project.test.organeHRM.utils.SeleniumWebActions;

public class TestContext {
    private final WebDriver driver;
    private final LoginPage loginPage;
    private final HomePage homePage;
    private final SeleniumWebActions webActions;
    private final FileActions fileActions;

    public TestContext() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage();
        this.homePage = new HomePage();
        this.webActions = new SeleniumWebActions();
        this.fileActions= new FileActions();
    }

    // Getters
    public WebDriver getDriver() { return driver; }
    public LoginPage getLoginPage() { return loginPage; }
    public HomePage getHomePage() { return homePage; }
    public SeleniumWebActions getWebActions() { return webActions; }
    public FileActions getFileActions() { return fileActions; }
    
}