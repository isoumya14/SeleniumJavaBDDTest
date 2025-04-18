package test;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.Assert;

public class LoginTest extends BaseTest {

	@Test
	public void validateLogin() throws IOException {
		// Verify page loaded
		Assert.assertTrue(context.getLoginPage().isPageLogoDisplayed(60), "Login page not displayed");

		// Login with credentials
		context.getLoginPage().login("Stageuser1", "StageSIT@123");

		// Verify error message if needed
		// Check for error (with shorter wait)

		if (context.getLoginPage().isPasswordErrorMessageDisplayed()) {
			System.out.println("Error: " + context.getLoginPage().getInvalidPasswordErrMsg());
		}

		context.getWebActions().assertWithWait(() -> context.getLoginPage().isLoginPageDisplayed(),
				"Login page not displayed", 60);

	}

}
