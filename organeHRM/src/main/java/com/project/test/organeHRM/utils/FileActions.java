package com.project.test.organeHRM.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.test.organeHRM.drivers.DriverManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileActions {
	
	WebDriver driver;

	public FileActions() {
		this.driver = DriverManager.getDriver();
		PageFactory.initElements(driver, this);

	}
	
	
	public void uploadFile(By fileInputLocator, String filePath) {
	    driver.findElement(fileInputLocator).sendKeys(filePath);
	}
	
	// Check if file exists in downloads folder
	public boolean isFileDownloaded(String fileName, long timeoutSec) {
	    File dir = new File(System.getProperty("user.home") + "/Downloads");
	    File[] files = dir.listFiles();
	    long startTime = System.currentTimeMillis();

	    while (System.currentTimeMillis() - startTime < timeoutSec * 1000) {
	        for (File file : files) {
	            if (file.getName().contains(fileName)) {
	                file.delete(); // Clean up after verification
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	public List<String[]> readCSV(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath))
            .stream()
            .map(line -> line.split(","))
            .collect(Collectors.toList());
    }

    public void writeCSV(String filePath, List<String[]> data) throws IOException {
        List<String> lines = data.stream()
            .map(row -> String.join(",", row))
            .collect(Collectors.toList());
        Files.write(Paths.get(filePath), lines);
    }
    
//public List<HashMap<String, String>>  getJsonDataToMap() throws IOException {
//		
//		//json to string
//		String jsonContent = FileUtils.readFileToString(new File("/Users/soumyapaital/Documents/UI-Java-Framework/OrangeHRM/organeHRM/src/test/resources/test-data/SiteData.json"),
//				StandardCharsets.UTF_8);
//		//String to Hashmap  - Jackson databind
//		ObjectMapper mapper = new ObjectMapper();
//		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){
//		});
//		return data;
//		
//		
//	}
    
    private static final String TEST_DATA_PATH = "/test-data/SiteData.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public List<Map<String, String>> getJsonDataToMap() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream(TEST_DATA_PATH)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Test data file not found: " + TEST_DATA_PATH);
            }
            return mapper.readValue(inputStream, new TypeReference<List<Map<String, String>>>() {});
        }
    }

	

}
