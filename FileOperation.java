package com.file;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FileOperation {

	public WebDriver driver;
	String url = "";
	String verificationError = "";
	String destination = "C:\\WindowsDrive\\Selenium_Jar\\Files\\Download";

	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\WindowsDrive\\Selenium_Jar\\Driver\\Runner\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		url = "http://demo.automationtesting.in/Index.html";
	}

	@Test
	public void fileUpload() {
		try {

			driver.get(url);
			// by id
			driver.findElement(By.cssSelector("#btn2")).click();
			Thread.sleep(2000);
			// click on more button
			driver.findElement(By.xpath("//a[text()='More']")).click();
			// select the option file upload
			driver.findElement(By.xpath("//a[text()='File Upload']")).click();
			// create an object of upload button
			WebElement browse = driver.findElement(By.xpath("//input[@id='input-4']"));
			// give the of file to be upload
			browse.sendKeys("C:\\WindowsDrive\\Selenium_Jar\\Files\\Upload\\File.txt");
			// certify the file upload
			if (driver.findElement(By.xpath("//span[text()='Remove']")).isDisplayed()) {
				System.out.println("File uploaded successfully!!!!");
			}

		} catch (Exception e) {
			// TODO: handle exception
			verificationError = e.getMessage();
			System.out.println(verificationError);
		}
	}

	@Test
	public void fileDownload() {
		try {

			driver.get(url);
			// by id
			driver.findElement(By.cssSelector("#btn2")).click();
			Thread.sleep(2000);
			// click on more button
			driver.findElement(By.xpath("//a[text()='More']")).click();
			// select the option file download option
			driver.findElement(By.xpath("//a[text()='File Download']")).click();
			// get the download button web element
			WebElement downloadButton = driver.findElement(By.xpath("//a[@type='button']"));
			// get the source location
			String sourceLocation = downloadButton.getAttribute("href");
			System.out.println("Source location of file is - " + sourceLocation);

			// create command
			String wgetCommand = "cmd /c C:\\WindowsDrive\\Selenium_Jar\\Files\\Download\\Wget\\wget.exe -P "
					+ destination + " --no-check-certificate " + sourceLocation + "";

			try {
				Process exec = Runtime.getRuntime().exec(wgetCommand);
				exec.waitFor(20, TimeUnit.SECONDS);
				exec.destroy();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

		} catch (Exception e) {
			// TODO: handle exception
			verificationError = e.getMessage();
			System.out.println(verificationError);
		}
	}

	@After
	public void tierdown() {
		driver.quit();
		if (!verificationError.equals("")) {
			Assert.fail();
		}
	}
}
