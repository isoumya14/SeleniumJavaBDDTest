package test;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginValidation extends BaseTest {

	@Test(description = "Verify successful login with valid credentials")
	public void testSuccessfulLogin() {
		// Wait for logo (up to 60s)
//	  // Verify page loaded
		Assert.assertTrue(context.getLoginPage().isPageLogoDisplayed(60), "Login page not displayed");

		// Login with credentials
		context.getLoginPage().login("Stageuser1", "StageSIT@123");

		context.getWebActions().assertWithWait(() -> context.getLoginPage().isLoginPageDisplayed(),
				"Login page not displayed", 60);

	}

	@Test(description = "Verify error message for wrong password")
	public void testInvalidPassword() {
		context.getLoginPage().login("Stageuser1", "wrongPass");

		// Check error appears quickly (5s max)
		context.getWebActions().assertWithWait(() -> context.getLoginPage().isPasswordErrorMessageDisplayed(),
				"Expected error message not shown", 5);

		// Verify exact error text
		Assert.assertEquals(context.getLoginPage().getInvalidPasswordErrMsg(), "Invalid credentials",
				"Incorrect error message");
	}

	@Test(description = "Verify validation for blank username")
	public void testEmptyUsername() {
		context.getLoginPage().login("", "anyPassword");

		context.getWebActions().assertWithWait(() -> context.getLoginPage().isUserNameErrorMessageDisplayed(),
				"Expected error message not shown", 5);
		// Verify exact error text
		Assert.assertEquals(context.getLoginPage().getInvalidUserNameErrorMessageText(), "Invalid credentials",
				"Incorrect error message");

	}
	
	@SuppressWarnings("deprecation")
	@Test(description = "Verify password field masks input")
	public void testPasswordMasking() {
	    WebElement passwordField = context.getLoginPage().getPasswordInput();
	    passwordField.sendKeys("visibleText");
	    
	   Assert.assertEquals(
	        passwordField.getAttribute("type"),
	        "password",
	        "Password field not masked"
	    );
	}

}
