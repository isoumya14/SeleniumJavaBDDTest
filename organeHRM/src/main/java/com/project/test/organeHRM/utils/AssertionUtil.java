package com.project.test.organeHRM.utils;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class AssertionUtil {
    private final WebDriver driver;
    
    public AssertionUtil(WebDriver driver) {
        this.driver = driver;
    }
    
    // 1. Text verification in multiple elements
    public void verifyTextInListElements(By locator, String expectedText) {
        List<WebElement> elements = driver.findElements(locator);
        boolean found = elements.stream()
                             .anyMatch(e -> e.getText().contains(expectedText));
        Assert.assertTrue(found, 
            String.format("Text '%s' not found in any element located by %s", 
                         expectedText, locator));
    }
    
    // 2. URL assertion with custom message
    public void assertURLContains(String expectedText) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedText),
            String.format("URL assertion failed. Expected '%s' in URL, but found '%s'", 
                         expectedText, currentUrl));
    }
    
    // 3. Enhanced element visibility check
    public void assertElementVisible(By locator) {
        WebElement element = driver.findElement(locator);
        Assert.assertTrue(element.isDisplayed(),
            String.format("Element %s is not visible", locator));
    }
    
    // 4. Exact text match assertion
    public void assertTextEquals(By locator, String expectedText) {
        String actualText = driver.findElement(locator).getText();
        Assert.assertEquals(actualText, expectedText,
            String.format("Text mismatch. Expected: '%s', Actual: '%s'",
                         expectedText, actualText));
    }
    
    // 5. Soft assertion version (for collecting multiple failures)
    public void softAssertTextEquals(By locator, String expectedText, SoftAssert softAssert) {
        String actualText = driver.findElement(locator).getText();
        softAssert.assertEquals(actualText, expectedText,
            String.format("Text mismatch. Expected: '%s', Actual: '%s'",
                         expectedText, actualText));
    }
    
    // 6. Verify element is clickable
    public void assertElementClickable(By locator) {
        WebElement element = driver.findElement(locator);
        Assert.assertTrue(element.isDisplayed() && element.isEnabled(),
            String.format("Element %s is not clickable", locator));
    }
    
    // 7. Verify element is present in DOM (may not be visible)
    public void assertElementPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        Assert.assertFalse(elements.isEmpty(),
            String.format("Element %s not found in DOM", locator));
    }
}