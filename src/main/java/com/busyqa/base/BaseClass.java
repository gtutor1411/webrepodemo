package com.busyqa.base;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

	public static void takeSnapshot(WebDriver driver, String screenshotName, ExtentTest test) throws IOException {

		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(file, new File(System.getProperty("user.dir") + "/Reports/" + screenshotName + ".png"));
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(screenshotName + ".png"));

	}

}
