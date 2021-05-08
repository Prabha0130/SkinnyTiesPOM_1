package com.qa.skinyties.testscript;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.skinyties.base.BasePage;
import com.qa.skinyties.pages.HomePage;
import com.qa.skinyties.pages.ProductPage;

public class CartTest extends BasePage{
	
	BasePage basePage;
	WebDriver driver;
	Properties prop;
	HomePage homePage;
	ProductPage productPage;
	
	@BeforeSuite
	public void SetupReport() {
		BasePage.spark = new ExtentSparkReporter(
				new File(System.getProperty("user.dir") + "./src/main/java/com/qa/skinyties/testreports/"
						+ BasePage.dateformat.format(BasePage.date) + "_SkinyTies_Test.html"));
		BasePage.extent = new ExtentReports();
		BasePage.extent.attachReporter(BasePage.spark);
	}
	
	@BeforeTest
	public void setup(){
		
		basePage = new BasePage();
		prop = basePage.initializePropertyFile();
		basePage.initializePropertyFile();
		driver = basePage.initializeBrowser(prop.getProperty("browser"));
		driver.get(prop.getProperty("url"));
	}
	
	@Test(priority=1)
	public void verifyHomePage() {
		BasePage.logger = BasePage.extent.createTest("Verify HomePage");
		homePage =new HomePage(driver);
		homePage.VerifyHomePageTitle();
			
	}
	
	@Test(priority=2)
	public void verifyAvailProduct(){
		BasePage.logger = BasePage.extent.createTest("Select Available Tie");
		homePage.selectTieInStock();
		homePage.verifyProductIsSelected();
	}
	
	@Test(priority=3,dependsOnMethods={"verifyAvailProduct"})
	public void VerifyAddToCart(){
		BasePage.logger = BasePage.extent.createTest("Add to cart test");
		productPage= new ProductPage(driver);
		productPage.openCart();
		productPage.verifyCartIsEmpty();
		productPage.addTieToCart();
		productPage.verifyCartIsUpdated();
	}
	
	@Test(priority=4)
	public void VerifyRemoveTieFromCart(){
		BasePage.logger = BasePage.extent.createTest("Remove Tie from the cart");
		productPage.removeTieFromCart();
		//productPage.verifyCartIsEmpty();
	}
	
	@AfterTest
	public void flush(){
		driver.quit();
	}

	@AfterSuite
	public void reportFlush() {
		BasePage.extent.flush();
	}
	

}
