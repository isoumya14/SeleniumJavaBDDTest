package com.project.test.organeHRM.drivers;

import org.openqa.selenium.WebDriver;
import java.util.Objects;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    private DriverManager() {} // Prevent instantiation
    
    public static WebDriver getDriver() {
        if (Objects.isNull(driver.get())) {
            throw new IllegalStateException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver.get();
    }
    
    public static void setDriver(WebDriver driverInstance) {
        driver.set(Objects.requireNonNull(driverInstance));
    }
    
    public static void quitDriver() {
    	WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    
    }
    
    public static boolean isDriverActive() {
        return driver.get() != null;
    }
}