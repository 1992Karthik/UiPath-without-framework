package com.leafBot.testcases;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;

import com.leafBot.testng.api.base.ProjectSpecificMethods;

public class TC001_UiPath extends ProjectSpecificMethods
{
	@BeforeTest
	public void setData() 
	{
		excelFileName="UiPath";
		testcaseName="Ui Path";
		testcaseDec = "Login and read the country name of a vendor";
		author="Sowmiya";
		//category="smoke";
	}
	
	@Test(dataProvider="fetchData")
	public void uiPath(String username,String password,String vendor)
	{
	System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
	ChromeDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.get("https://acme-test.uipath.com/account/login");
	driver.findElementById("email").sendKeys(username);
	driver.findElementById("password").sendKeys(password, Keys.ENTER);
	driver.findElementByXPath("//button[text()='Log In']").click();
	Actions ac = new Actions(driver);
	WebElement vendors = driver.findElementByXPath("(//button[@class='btn btn-default btn-lg'])[4]");
	ac.moveToElement(vendors).perform();
	driver.findElementByLinkText("Search for Vendor").click();
	driver.findElementById("vendorName").sendKeys(vendor);
	driver.findElementByXPath("//button[text()='Search']").click();
	WebElement vendorTable = driver.findElementByTagName("table");
	List<WebElement> allRows = vendorTable.findElements(By.tagName("tr"));
	WebElement firstRow = allRows.get(1);
	List<WebElement> allColumns = firstRow.findElements(By.tagName("td"));
	int columnSize = allColumns.size();
	System.out.println(columnSize);
	String country = allColumns.get(columnSize-1).getText();
	//WebElement country = driver.findElementByXPath("(//td)[5]");
	System.out.println(country);
	driver.findElementByLinkText("Log Out").click();
	driver.close();
}
}
