package com.project.test.organeHRM.utils;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileActions {
	
	WebDriver driver;
	
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

}
