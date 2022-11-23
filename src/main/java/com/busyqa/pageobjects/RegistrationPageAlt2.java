package com.busyqa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPageAlt2 {

	WebDriver driver;

	private static WebElement element = null;

	public RegistrationPageAlt2(WebDriver driver) {
		this.driver = driver;

	}

	public WebElement tboxFirstName() {
		element = driver.findElement(By.id("input-firstname"));
		return element;
	}
	
	
	public WebElement tboxlastName() {
		element = driver.findElement(By.id("input-lastname"));
		return element;
	}


}
