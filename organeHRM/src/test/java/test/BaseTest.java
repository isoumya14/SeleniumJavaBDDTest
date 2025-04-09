package test;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import com.project.test.organeHRM.drivers.DriverManager;
import com.project.test.organeHRM.drivers.TestContext;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	protected TestContext context;
    protected Properties prop;


	@BeforeMethod
    public void setUp() throws IOException {
		try {
            initializeDriver();
            Assert.assertTrue(DriverManager.isDriverActive(), 
                    "Driver failed to initialize");
            context = new TestContext();
        } catch (Exception e) {
            throw new RuntimeException("Test initialization failed", e);
        }
    }

    protected void initializeDriver() throws IOException {
        prop = loadProperties();
        WebDriver driverInstance = createDriverInstance();
        DriverManager.setDriver(driverInstance);
        configureDriver();
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        String configPath = System.getProperty("user.dir") + "/src/main/resources/config.properties";
        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
        }
        return properties;
    }

    private WebDriver createDriverInstance() {
    	 String browserName = prop.getProperty("browser", "chrome").trim().toLowerCase();
    	 boolean headless = Boolean.parseBoolean(prop.getProperty("headless", "false"));
         boolean acceptSSL = Boolean.parseBoolean(prop.getProperty("accept.ssl.certs", "true"));
         
         
        if (browserName == null) {
            throw new IllegalArgumentException("Browser not specified in config.properties");
        }

        switch (browserName.toLowerCase()) {
            case "chrome":
                return createChromeDriver(headless, acceptSSL);
            case "firefox":
                return createFirefoxDriver(headless, acceptSSL);
            case "edge":
                return createEdgeDriver(headless, acceptSSL);
            case "safari":
                return createSafariDriver(acceptSSL);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
    }

    private WebDriver createChromeDriver(boolean headless, boolean acceptSSL) {
        WebDriverManager.chromedriver().clearDriverCache().clearResolutionCache().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        if (acceptSSL) {
            options.setAcceptInsecureCerts(true);
            options.addArguments("--ignore-certificate-errors");
        }
        return new ChromeDriver(options);
    }

    private WebDriver createFirefoxDriver(boolean headless, boolean acceptSSL) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) options.addArguments("--headless");
        if (acceptSSL) {
            options.setAcceptInsecureCerts(true);
            options.addArguments("--ignore-certificate-errors");
        }
        return new FirefoxDriver(options);
    }

    private WebDriver createEdgeDriver(boolean headless, boolean acceptSSL) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        if (headless) options.addArguments("--headless=new");
        if (acceptSSL) {
            options.setAcceptInsecureCerts(true);
            options.addArguments("--ignore-certificate-errors");
        }
        return new EdgeDriver(options);
    }

    private WebDriver createSafariDriver(boolean acceptSSL) {
        if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
            throw new IllegalStateException("Safari is only available on macOS");
        }
        SafariOptions options = new SafariOptions();
        if (acceptSSL) options.setAcceptInsecureCerts(true);
        return new SafariDriver(options);
    }

    private void configureDriver() {
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(prop.getProperty("implicit.wait", "60"))));
        getDriver().manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(Long.parseLong(prop.getProperty("page.load.timeout", "90"))));
        
        String url = prop.getProperty("url");
        if (url == null || url.isBlank())  {
            throw new IllegalArgumentException("URL not specified in config.properties");
        }
        getDriver().get(url);
    }

    protected WebDriver getDriver() {
       
    	 try {
             return DriverManager.getDriver();
         } catch (IllegalStateException e) {
             throw new IllegalStateException("WebDriver not initialized. Check if @BeforeMethod executed successfully.", e);
         }
     }
    

    @AfterMethod(alwaysRun = true)	
//	@AfterTest
    public void tearDown() {
    	
    	try {
            // Safe quit - checks if driver exists first
            if (DriverManager.getDriver() != null) {
                DriverManager.quitDriver();
            }
        } catch (IllegalStateException e) {
            // Driver was never initialized - no action needed
        	System.err.println("Error during teardown: " + e.getMessage());
        }
    }

    protected void waitForPageLoad() {
        getDriver().manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(Long.parseLong(prop.getProperty("page.load.timeout"))));
    }

    protected void setImplicitWait(long seconds) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    protected void disableImplicitWait() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
    }
}