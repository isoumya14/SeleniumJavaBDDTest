package com.project.test.organeHRM.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class JsUtils {
    private WebDriver driver;

    public JsUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Executes asynchronous JavaScript and waits for callback.
     * @param script JavaScript code with callback (must call `arguments[arguments.length - 1]`)
     * @return Result of the script execution
     * @throws WebDriverException if script fails or timeout occurs
     */
    public Object executeAsyncJS(String script) throws WebDriverException {
        if (!(driver instanceof JavascriptExecutor)) {
            throw new WebDriverException("Driver does not support JavaScript execution");
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeAsyncScript(script);
    }
}