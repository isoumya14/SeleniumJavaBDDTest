package test;

import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.project.test.organeHRM.drivers.DriverManager;

public class BrowserValidation extends BaseTest{
	
	
	@Test(description = "Verify session after browser back")
	public void testBrowserBackAfterLogin() {
	    context.getLoginPage().login("validUser", "validPass123");
	    context.getHomePage().verifyDashboardLoaded();
	    
	    // Simulate browser back
	    DriverManager.getDriver().navigate().back();
	    
	    // Should redirect to login or show session expired
	    context.getWebActions().assertWithWait(
	        () -> !context.getHomePage().isDashboardLoaded(),
	        "Session not terminated after back navigation",
	        5
	    );
	}
	
	@Test(description = "Verify single session per user")
	public void testConcurrentLogin() {
	    // First login
	    context.getLoginPage().login("user1", "pass1");
	    
	    // Open new window and try same credentials
	    String originalWindow = DriverManager.getDriver().getWindowHandle();
	    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
	    js.executeScript("window.open()");
	    
	    Set<String> handles = DriverManager.getDriver().getWindowHandles();
	    handles.remove(originalWindow);
	    DriverManager.getDriver().switchTo().window(handles.iterator().next());
	    
	    DriverManager.getDriver().get(prop.getProperty("url"));
	    context.getLoginPage().login("user1", "pass1");
	    
	    // Verify appropriate message
	    context.getWebActions().assertWithWait(
	        () -> context.getLoginPage().isConcurrentSessionMessageDisplayed(),
	        "Expected concurrent session warning missing",
	        5
	    );
	}

}
