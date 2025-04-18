package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SiteDataTest extends BaseTest {

	//@Test(dataProvider = "siteNames")
	public void validateSiteData(String siteName) throws InterruptedException {

		Assert.assertTrue(context.getLoginPage().isPageLogoDisplayed(60), "Login page not displayed");
		context.getLoginPage().login("Stageuser1", "StageSIT@123");
		if (context.getLoginPage().isPasswordErrorMessageDisplayed()) {
			System.out.println("Error: " + context.getLoginPage().getInvalidPasswordErrMsg());
		}
		context.getWebActions().assertWithWait(() -> context.getLoginPage().isLoginPageDisplayed(),
				"Login page not displayed", 60);

		context.getHomePage().selectSiteFromDropdown(siteName);
		context.getHomePage().verifySiteSelected(siteName);

	}

	@Test(dataProvider = "siteValues")
	public void validateSiteDropDown(HashMap<String, String> siteName) throws InterruptedException {

		Assert.assertTrue(context.getLoginPage().isPageLogoDisplayed(60), "Login page not displayed");
		context.getLoginPage().login("Stageuser1", "StageSIT@123");
		if (context.getLoginPage().isPasswordErrorMessageDisplayed()) {
			System.out.println("Error: " + context.getLoginPage().getInvalidPasswordErrMsg());
		}
		context.getWebActions().assertWithWait(() -> context.getLoginPage().isLoginPageDisplayed(),
				"Login page not displayed", 60);

		context.getHomePage().selectSiteFromDropdown(siteName.get("siteName"));
		context.getHomePage().verifySiteSelected(siteName.get("siteName"));

	}

	@DataProvider(name = "siteNames")
	public Object[][] getData() {
		return new Object[][] { { "Chemours" }, { "Illinois" }, { "PSASite1" } };
	}

	@DataProvider(name = "siteValues")
	public Object[][] getDataInJson() throws IOException {

		List<Map<String, String>> siteNameData = context.getFileActions().getJsonDataToMap();
return new Object[][] { { siteNameData.get(0) }, { siteNameData.get(1) } };

	}
}
