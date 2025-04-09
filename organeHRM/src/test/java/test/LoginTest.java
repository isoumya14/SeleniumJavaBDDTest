package test;

import org.testng.annotations.Test;

import com.project.test.organeHRM.drivers.DriverManager;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

public class LoginTest extends BaseTest {

	@Test
	public void validateLogin() throws IOException {
		// Verify page loaded
		Assert.assertTrue(context.getLoginPage().isPageLogoDisplayed(60), "Login page not displayed");

		// Login with credentials
		context.getLoginPage().login("Stageuser1", "StageSIT@123");

		// Verify error message if needed
		//Check for error (with shorter wait)
		
		if (context.getLoginPage().isErrorMessageDisplayed()) {
			System.out.println("Error: " + context.getLoginPage().getInvalidPasswordErrMsg());
		}

		context.getWebActions().assertWithWait(
               () -> context.getLoginPage().isLoginPageDisplayed(), "Login page not displayed", 60 																								
		);

	}
	
	@Test(description = "Verify successful login with valid credentials")
	public void testSuccessfulLogin() {
	    // Wait for logo (up to 60s)
//	  // Verify page loaded
		Assert.assertTrue(context.getLoginPage().isPageLogoDisplayed(60), "Login page not displayed");

		// Login with credentials
		context.getLoginPage().login("Stageuser1", "StageSIT@123");

		context.getWebActions().assertWithWait(
	               () -> context.getLoginPage().isLoginPageDisplayed(), "Login page not displayed", 60 																								
			);

		}
	    
	    
		
		
//	    context.getWebActions().assertWithWait(
//	        () -> context.getHomePage().isWelcomeMessageDisplayed(),
//	        "Login failed - dashboard not shown",
//	        15
//	    );
	    
//	    public BooleanSupplier isLogoVisibleCondition() {
//	        return () -> this.pageLogo.isDisplayed();
//	    }

	}
	
//	@Test(description = "Verify error message for wrong password")
//	public void testInvalidPassword() {
//	    context.getLoginPage().login("validUser", "wrongPass");
//	    
//	    // Check error appears quickly (5s max)
//	    TestUtils.assertWithWait(
//	        () -> context.getLoginPage().isErrorMessageDisplayed(),
//	        "Expected error message not shown",
//	        5
//	    );
//	    
//	    // Verify exact error text
//	    Assert.assertEquals(
//	        context.getLoginPage().getErrorMessageText(),
//	        "Invalid credentials",
//	        "Incorrect error message"
//	    );
//	}
//	
//	@Test(description = "Verify validation for blank username")
//	public void testEmptyUsername() {
//	    context.getLoginPage().login("", "anyPassword");
//	    
//	    TestUtils.assertWithWait(
//	        () -> context.getLoginPage().isFieldErrorDisplayed("username"),
//	        "Username required error missing",
//	        3
//	    );
//	}
//	
//	@Test(description = "Verify password field masks input")
//	public void testPasswordMasking() {
//	    WebElement passwordField = context.getLoginPage().getPasswordField();
//	    passwordField.sendKeys("visibleText");
//	    
//	    Assert.assertEquals(
//	        passwordField.getAttribute("type"),
//	        "password",
//	        "Password field not masked"
//	    );
//	}
//	
//	@Test(description = "Verify session after browser back")
//	public void testBrowserBackAfterLogin() {
//	    context.getLoginPage().login("validUser", "validPass123");
//	    context.getHomePage().verifyDashboardLoaded();
//	    
//	    // Simulate browser back
//	    DriverManager.getDriver().navigate().back();
//	    
//	    // Should redirect to login or show session expired
//	    context.getWebActions().assertWithWaitt(
//	        () -> !context.getHomePage().isDashboardLoaded(),
//	        "Session not terminated after back navigation",
//	        5
//	    );
//	}
//	
//	@Test(description = "Verify single session per user")
//	public void testConcurrentLogin() {
//	    // First login
//	    context.getLoginPage().login("user1", "pass1");
//	    
//	    // Open new window and try same credentials
//	    String originalWindow = DriverManager.getDriver().getWindowHandle();
//	    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
//	    js.executeScript("window.open()");
//	    
//	    Set<String> handles = DriverManager.getDriver().getWindowHandles();
//	    handles.remove(originalWindow);
//	    DriverManager.getDriver().switchTo().window(handles.iterator().next());
//	    
//	    DriverManager.getDriver().get(Config.getBaseUrl());
//	    context.getLoginPage().login("user1", "pass1");
//	    
//	    // Verify appropriate message
//	    context.getWebActions().assertWithWait(
//	        () -> context.getLoginPage().isConcurrentSessionMessageDisplayed(),
//	        "Expected concurrent session warning missing",
//	        5
//	    );
//	}
//	
//	@Test(description = "Verify 'Remember Me' persists username")
//	public void testRememberMeFunctionality() {
//	    context.getLoginPage().checkRememberMe();
//	    context.getLoginPage().login("rememberUser", "rememberPass");
//	    context.getHomePage().logout();
//	    
//	    // Verify username persists
//	    Assert.assertEquals(
//	        context.getLoginPage().getUsernameFieldValue(),
//	        "rememberUser",
//	        "Username not remembered"
//	    );
//	    
//	    // Verify password is blank
//	    Assert.assertTrue(
//	        context.getLoginPage().getPasswordFieldValue().isEmpty(),
//	        "Password should not be remembered"
//	    );
//	}
//}