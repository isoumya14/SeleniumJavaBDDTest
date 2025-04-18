package com.project.test.organeHRM.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.project.test.organeHRM.drivers.DriverManager;

public class SeleniumWebActions {

	WebDriver driver;

	public SeleniumWebActions() {
		this.driver = DriverManager.getDriver();
		PageFactory.initElements(driver, this);

	}

	public WebElement waitForClickable(By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));

	}

	public WebElement waitForClickable(WebElement element, int timeoutInSeconds) {
		return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	// New method for Page Object patterns
	public WebElement waitForClickable(Supplier<WebElement> elementSupplier, int timeoutInSeconds) {
		return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(driver -> {
			try {
				return ExpectedConditions.elementToBeClickable(elementSupplier.get()).apply(driver);
			} catch (StaleElementReferenceException e) {
				return null; // Retry if stale
			}
		});
	}

	public  void assertWithWait(BooleanSupplier condition, String message, int timeoutSec) {
		try {
			new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSec))
					.pollingEvery(Duration.ofSeconds(1)).until(driver -> condition.getAsBoolean());
		} catch (TimeoutException e) {
			Assert.fail(message + " (Waited " + timeoutSec + " seconds)");
		}
	}
	
	
	
	/**
     * Waits for condition to be true before asserting
     * @param condition Lambda expression that returns boolean
     * @param message Assertion message
     * @param timeoutSec Maximum wait time in seconds
     */
    public static void assertWithWaitt(BooleanSupplier condition, 
                                    String message, 
                                    int timeoutSec) {
        try {
            new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSec))
                .pollingEvery(Duration.ofSeconds(1))
                .until(driver -> condition.getAsBoolean());
        } catch (TimeoutException e) {
            Assert.fail(message + " (Waited " + timeoutSec + " seconds)", e);
        }
    }
	
	
	
	public WebElement waitForVisibility(By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Wait for element to disappear
	public boolean waitForInvisibility(By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	// Wait for Page Load (Alternative to driver.manage().timeouts())
	public void waitForPageLoad(int timeoutInSeconds) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
	}

	// Select dropdown by visible text
	public void selectDropdownByText(By dropdownLocator, String text) {
		WebElement dropdown = driver.findElement(dropdownLocator);
		Select select = new Select(dropdown);
		select.selectByVisibleText(text);
	}

	// Select dropdown by value attribute
	public void selectDropdownByValue(By dropdownLocator, String value) {
		new Select(driver.findElement(dropdownLocator)).selectByValue(value);
	}

	public void jsClick(By locator) {
		WebElement element = driver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	// Scroll to element
	public void scrollToElement(By locator) {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// Switch to iframe by locator
	public void switchToFrame(By frameLocator) {
		driver.switchTo().frame(driver.findElement(frameLocator));
	}

	// Switch to new tab/window
	public void switchToNewWindow() {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public void dragAndDrop(By sourceLocator, By targetLocator) {
		WebElement source = driver.findElement(sourceLocator);
		WebElement target = driver.findElement(targetLocator);
		new Actions(driver).dragAndDrop(source, target).perform();
	}

	public void hoverOverElement(By locator) {
		new Actions(driver).moveToElement(driver.findElement(locator)).perform();
	}

	public void doubleClick(By locator) {
		new Actions(driver).doubleClick(driver.findElement(locator)).perform();
	}

	public void rightClick(By locator) {
		new Actions(driver).contextClick(driver.findElement(locator)).perform();
	}

	public void pressKeys(By locator, CharSequence... keys) {
		driver.findElement(locator).sendKeys(keys);
	}

	// Example: pressKeys(By.id("search"), Keys.CONTROL + "a", Keys.DELETE);

	public void highlightElement(By locator) {
		WebElement element = driver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// Take screenshot and save to folder
	public void takeScreenshot(String testName) {
	    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    try {
	        FileUtils.copyFile(src, new File("./screenshots/" + testName + "_" + System.currentTimeMillis() + ".png"));
	    } catch (IOException e) {
	        System.out.println("Screenshot failed: " + e.getMessage());
	    }
	}


}
