package com.busyqa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

	WebDriver driver;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
	}

	/*
	 * Identifying the element driver.findElement(By.id("input-firstname")) taking
	 * action .sendKeys(fName);
	 * driver.findElement(By.id("input-lastname")).sendKeys(lName);
	 * driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys(email);
	 * driver.findElement(By.xpath("//input[@id='input-telephone']")).sendKeys(phone
	 * ); driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys(
	 * password);
	 * driver.findElement(By.xpath("//input[@id='input-confirm']")).sendKeys(
	 * password);
	 */
	/*
	 * driver.findElement(By.
	 * xpath("//div[@class='alert alert-danger alert-dismissible']"))
	 * .isDisplayed();
	 */
	// LOCATORS
	By fName = By.id("input-firstname");
	By lName = By.id("input-lastname");
	By email = By.xpath("//input[@id='input-email']");
	By phone = By.xpath("//input[@id='input-telephone']");
	By password = By.xpath("//input[@id='input-password']");
	By passwordConfirm = By.xpath("//input[@id='input-confirm']");
	By chkboxAgree = By.xpath("//input[@name='agree']");
	By buttonContinue = By.xpath("//input[@value='Continue']");

	/*
	 * By redAlert =
	 * By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	 */
	// ACTIONS
	public void enterFname(String firstName) {
		driver.findElement(fName).sendKeys(firstName);
	}

	public void clearFname(String firstName) {
		driver.findElement(fName).clear();
	}

	public void enterLname(String lastName) {
		driver.findElement(lName).sendKeys(lastName);
	}

	public void enterEmail(String mail) {
		driver.findElement(email).sendKeys(mail);
	}

	public void enterPhone(String phoneNumber) {
		driver.findElement(phone).sendKeys(phoneNumber);
	}

	public void enterPassword(String pwd) {
		driver.findElement(password).sendKeys(pwd);
	}

	public void enterConfirmPasword(String pwd) {
		driver.findElement(passwordConfirm).sendKeys(pwd);
	}

	public void clickTermsAndConditions() {
		driver.findElement(chkboxAgree).click();
	}

	public void clickContinue() {
		driver.findElement(buttonContinue).click();
	}

	/*
	 * public void isRedAlertDisplayed() {
	 * driver.findElement(redAlert).isDisplayed(); }
	 */

	public void fillRegistrationForm(String fname, String lname, String email, String phone, String password) {
		enterFname(fname);
		enterLname(lname);
		enterEmail(email);
		enterPhone(phone);
		enterPassword(password);
		enterConfirmPasword(password);
	}

}
