package com.busyqa.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPageAlt {

	WebDriver driver;

	public RegistrationPageAlt(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*
	 * By fName = By.id("input-firstname"); By lName = By.id("input-lastname"); By
	 * email = By.xpath("//input[@id='input-email']"); By phone =
	 * By.xpath("//input[@id='input-telephone']"); By password =
	 * By.xpath("//input[@id='input-password']"); By passwordConfirm =
	 * By.xpath("//input[@id='input-confirm']"); By chkboxAgree =
	 * By.xpath("//input[@name='agree']"); By buttonContinue =
	 * By.xpath("//input[@value='Continue']");
	 */
	@FindBy(id = "input-firstname")
	public WebElement tboxFirstName;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	public WebElement redAlert;

	public void enterFname(String firstName) {
		tboxFirstName.sendKeys(firstName);
	}

}
