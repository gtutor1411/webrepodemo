package com.busyqa.auto;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.busyqa.base.BaseClass;
import com.busyqa.pageobjects.RegistrationPage;
import com.busyqa.pageobjects.RegistrationPageAlt;
import com.busyqa.pageobjects.RegistrationPageAlt2;
import com.busyqa.utils.DataStream;
import com.busyqa.utils.ExcelUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTests {

	WebDriver driver;
	Properties prop;
	String browser;
	String userEmail;
	String rootPath;
	ExtentReports report;
	String screenshotpath;
	ExcelUtil excel;
	Logger log = LogManager.getLogger(WebTests.class);

	@BeforeTest
	public void setup() {
		try {
			// loading properties file

			rootPath = System.getProperty("user.dir");
			excel = new ExcelUtil(rootPath + "\\src\\main\\java\\com\\busyqa\\data\\TestData.xlsx", "ENV");
			FileInputStream fis = new FileInputStream(rootPath + "\\config.properties");
			prop = new Properties();
			prop.load(fis);
			userEmail = prop.getProperty("username");

			// reading the env
			String runEnv = excel.getCellValue(0, 1);
			// String runEnv = prop.getProperty("env");
			if (runEnv.equalsIgnoreCase("DEV")) {
				System.out.println("RUNNING TESTS IN DEV ENV");
			}
			if (runEnv.equalsIgnoreCase("STAGING")) {
				System.out.println("RUNNING TESTS IN STAGING ENV");
			}

			// reading browser
			browser = prop.getProperty("browser");
			report = new ExtentReports(rootPath + "/Reports/Results.html");
			screenshotpath = rootPath + "/Reports/";
			log.info("Initiated Testing Suite");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void initiateBrowser() {
		try {
			// launching browser
			if (browser.equalsIgnoreCase("CH")) {
				log.info("Initiated Testing Suite in CHROME Browser");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}
			if (browser.equalsIgnoreCase("ED")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}

			if (browser.equalsIgnoreCase("FF")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		log.info("Closed Browser");

	}

	@AfterTest
	public void superTearDown() {
		log.info("Reporting completed");
		report.flush();

	}

	@Test(priority = 0, enabled = true)
	public void titleTest() {
		boolean result = true;
		ExtentTest test = report.startTest("Verify Title in browser");
		try {
			driver.get("https://naveenautomationlabs.com/opencart/");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
			BaseClass.takeSnapshot(driver, "titleTest", test);
			if (driver.getTitle().equalsIgnoreCase("Your Store")) {
				test.log(LogStatus.PASS, "The title is appearing as expected");
			} else {
				test.log(LogStatus.FAIL, "The title is appearing as expected");
				result = false;
			}
			report.endTest(test);
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception found in initial test");
			log.error(e);
			report.endTest(test);
		}
	}

	@Test(priority = 1, dataProvider = "registerData", enabled = false)
	public void registerTest(String fName, String lName, String email, String phone, String password) {
		boolean result1 = true;
		boolean result2 = true;
		RegistrationPage rp = new RegistrationPage(driver);
		RegistrationPageAlt rpa = new RegistrationPageAlt(driver);
		RegistrationPageAlt2 rpa2 = new RegistrationPageAlt2(driver);
		ExtentTest test = report.startTest("Verify Registering new user");
		try {
			test.log(LogStatus.INFO, "Navigating to website url");
			driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");
			test.log(LogStatus.INFO, "Entering user Details");
			BaseClass.takeSnapshot(driver, "resgtest1", test);
			// rp.fillRegistrationForm(fName, lName, email, phone, password);
			rpa.enterFname(fName);
			rpa2.tboxFirstName().sendKeys("thisismyway");
			BaseClass.takeSnapshot(driver, "resgtest2", test);
			test.log(LogStatus.INFO, "Clicking on terms and conditions");
			rp.clickTermsAndConditions();
			test.log(LogStatus.INFO, "Clicking on COntinue button");
			rp.clickContinue();
			boolean isErrorVisible;
			try {
				isErrorVisible = rpa.redAlert.isDisplayed();
			} catch (Exception e) {
				isErrorVisible = false;
			}

			// CUSTOM REPORTING
			if (!isErrorVisible) {
				test.log(LogStatus.PASS, "No form errors found");
			} else {
				test.log(LogStatus.FAIL, "Form errors found");
				result1 = false;
			}

			String registerSuccessMessage = driver.findElement(By.xpath(".//h1")).getText();
			// assertEquals("Your Account Has Been Created!", registerSuccessMessage);
			if (registerSuccessMessage.equalsIgnoreCase("Your Account Has Been Created!")) {
				BaseClass.takeSnapshot(driver, "pass2", test);
				test.log(LogStatus.PASS, "Registration tests passed");
			} else {
				test.log(LogStatus.FAIL, "Registration test failed");
				BaseClass.takeSnapshot(driver, "fail2", test);
				result2 = false;
			}
			report.endTest(test);
			// TESTNG REPORTING
			assertTrue(result1 && result2);
		} catch (Exception e) {
			e.printStackTrace();
			report.endTest(test);
		}

	}

	@Test(priority = 2, enabled = false)
	public void loginTest() {
		boolean result = true;
		ExtentTest test = report.startTest("Verify Registering new user");
		try {
			test.log(LogStatus.INFO, "Navigating to login url");
			driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
			driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys(userEmail);
			driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys("password0");
			test.log(LogStatus.INFO, "Entered username and password");
			BaseClass.takeSnapshot(driver, "login", test);
			driver.findElement(By.xpath("//input[@value='Login']")).click();
			try {
				driver.findElement(By.xpath("//a[normalize-space()='Edit your account information']"));
				BaseClass.takeSnapshot(driver, "login", test);
				test.log(LogStatus.PASS, "Login successfull");
			} catch (NoSuchElementException nse) {
				nse.printStackTrace();
				test.log(LogStatus.PASS, "Login failed");
				result = false;
			}
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@DataProvider(name = "registerData")
	public Object[][] registerData() {
		DataStream data = new DataStream();
		Object[][] returnData = data.dataStreamer("RegistrationData2");
		return returnData;
	}

	@DataProvider(name = "loginData")
	public Object[][] loginData() {
		DataStream data = new DataStream();
		Object[][] returnData = data.dataStreamer("LoginData");
		return returnData;
	}

}
