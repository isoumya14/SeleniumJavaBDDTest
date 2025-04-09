package com.project.test.organeHRM.utils;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class waitUtil {
	private WebDriver driver;

	public void WaitUtils(WebDriver driver) {
		this.driver = driver;
	}


	public static class WaitUtils {

		// Generic wait method that works with any condition type
		public static <T> T waitFor(Function<WebDriver, T> condition, WebDriver driver, int timeoutSec) {
			return new WebDriverWait(driver, Duration.ofSeconds(timeoutSec)).pollingEvery(Duration.ofMillis(500))
					.ignoring(NoSuchElementException.class).until(condition);
		}

		// Specific method for visibility checks
		public static boolean waitForDisplayed(WebElement element, WebDriver driver, int timeoutSec) {
			try {
				waitFor(ExpectedConditions.visibilityOf(element), driver, timeoutSec);
				return true;
			} catch (TimeoutException e) {
				return false;
			}
		}

		// Specific method for boolean conditions
		public boolean waitForCondition(Function<WebDriver, Boolean> condition, WebDriver driver, int timeoutSec) {
			try {
				return waitFor(condition, driver, timeoutSec);
			} catch (TimeoutException e) {
				return false;
			}
		}
	}
}
