package com.alertandwindows;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class LearnAlertAndWindows {

	public WebDriver driver;
	String url = "";
	String verificationError = "";

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
	public void getAlerts() {
		try {
			driver.get(url);
			// click on skip sign in button
			driver.findElement(By.xpath("//button[@id='btn2']")).click();

			// click on the switch to drop down
			driver.findElement(By.xpath("//a[text()='SwitchTo']")).click();

			// click on the alert list option
			driver.findElement(By.xpath("//a[text()='Alerts']")).click();

			// click on the button to open simple aler
			driver.findElement(By.xpath("//button[@class='btn btn-danger']")).click();

			// switching to alert
			Alert alert = driver.switchTo().alert();
			Thread.sleep(2000);

			// get the alert text and print
			String getText = alert.getText();
			System.out.println(getText);

			// accept the alert box
			alert.accept();

			// this way of verification will not fail your script if object is not there on
			// the screen
			if (driver.findElements(By.xpath("//button[contains(text(),'click the button to display an  alert box')]"))
					.size() > 0) {
				System.out.println("Alert is accepted and control come back to the main screen");
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void getTabMovement() {
		try {
			driver.get(url);
			// click on skip sign in button
			driver.findElement(By.xpath("//button[@id='btn2']")).click();

			// click on the switch to drop down
			driver.findElement(By.xpath("//a[text()='SwitchTo']")).click();

			// click on the window dropdown option
			driver.findElement(By.xpath("//a[text()='Windows']")).click();

			// click on open new tab window
			driver.findElement(By.xpath("//a//button[@class='btn btn-info']")).click();

			// get window handle ids into list
			ArrayList<String> windowIds = new ArrayList<String>(driver.getWindowHandles());

			// move to the next tab
			Actions action = new Actions(driver);
			action.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).build().perform();

			driver.switchTo().window(windowIds.get(1));

			System.out.println(driver.getTitle());

			driver.close();

			driver.switchTo().window(windowIds.get(0));
			System.out.println(driver.getTitle());

		} catch (Exception ex) {
			verificationError = ex.getMessage();
		}
	}

	@Test
	public void getWindowMovement() {
		try {

			driver.get(url);
			// click on skip sign in button
			driver.findElement(By.xpath("//button[@id='btn2']")).click();

			// click on the switch to drop down
			driver.findElement(By.xpath("//a[text()='SwitchTo']")).click();

			// click on the window dropdown option
			driver.findElement(By.xpath("//a[text()='Windows']")).click();

			// click on
			driver.findElement(By.xpath("//a[text()='Open New Seperate Windows']")).click();

			// I want window id of main window
			String mainWindowhandleId = driver.getWindowHandle();

			// click on the button
			driver.findElement(By.xpath("//button[text()='click']")).click();

			Thread.sleep(2000);

			// get window handle ids into list
			ArrayList<String> windowIds = new ArrayList<String>(driver.getWindowHandles());

			for (String window : windowIds) {
				if (!window.equalsIgnoreCase(mainWindowhandleId)) {
					// move to child window
					driver.switchTo().window(window);
					System.out.println("New window title is : " + driver.getTitle());
					driver.close();
				}
			}

			driver.switchTo().window(mainWindowhandleId);
			System.out.println(driver.getTitle());

		} catch (Exception e) {
			// TODO: handle exception
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
