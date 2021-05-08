package com.qa.skinyties.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.skinyties.base.BasePage;

import net.bytebuddy.implementation.bytecode.Throw;

public class ElementUtils extends BasePage {

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions action;

	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
	}

	public WebElement findElement(By locator) {
		WebElement element;

		try {
			if (locator != null) {
				element = driver.findElement(locator);
				return element;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendKeys(By locator, String value, String fieldName) {
		try {
			findElement(locator).sendKeys(value);
			BasePage.logger.pass(value + "is entered in " + fieldName);

		} catch (Exception e) {
			BasePage.logger.fail("Unable to perform sendKeys" + fieldName,
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			e.printStackTrace();
		}
	}

	public void clickElement(By locator, String ElementName) {
		try {
			findElement(locator).click();
			BasePage.logger.pass(ElementName + " is clicked.");
		} catch (Exception e) {
			BasePage.logger.fail(ElementName + " is not clickable.",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			// Assert.fail();
		}
		}

	public void VerifyCurrentPageTitle(String expectedTitle, String PageName) {
		String actualTitle;
		try {
			waitUntilTitleContains(expectedTitle, 20);
			actualTitle = driver.getTitle();
			if (actualTitle.equals(expectedTitle)) {
				BasePage.logger.pass(
						" Successfully navigated to " + PageName + ", current url is: " + getCurrentPageURL(),
						MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			} else {
				throw new Exception(PageName + " title doesnt match with expected title");
			}
		}

		catch (Exception e) {
			BasePage.logger
					.fail(PageName + " Navigation Failed, Page title not matched",
							MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build())
					.fail(e.getStackTrace().toString());
			BasePage.logger.generateLog(Status.FAIL, e.getStackTrace().toString());
			BasePage.extent.flush();
			Assert.fail(PageName + "Navigation Failed title not matched");
		}

	}
	
	public String getText(By locator) {
		try{
		String text = findElement(locator).getText();
		findElement(locator).getAttribute("value");
		return text;
		}catch(Exception e){
			e.printStackTrace();
		}return null;
	}
	
	public String getAttribute(By locator,String attributeName) {
		try{
		String text = findElement(locator).getAttribute(attributeName);
		return text;
		}catch(Exception e){
			e.printStackTrace();
		}return null;
	}
	

	public void waitUntilTitleContains(String title, long Seconds) {
		wait = new WebDriverWait(driver, Seconds);
		try {
			wait.until(ExpectedConditions.titleContains(title));
			//BasePage.logger.info("Page Title contains expected title");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitUntilElementPresent(By locator, long seconds) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public void waitUntilElementVisible(By locator, long Seconds) {
		wait = new WebDriverWait(driver, Seconds);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCurrentPageURL() {
		try {
			String url = driver.getCurrentUrl();
			return url;
		} catch (Exception e) {
			BasePage.logger.info("Unable to fetch current page URL");
		}
		return null;
	}
	
	
	public void waitUntilElementClickable(By locator, long Seconds) {
		wait = new WebDriverWait(driver, Seconds);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
