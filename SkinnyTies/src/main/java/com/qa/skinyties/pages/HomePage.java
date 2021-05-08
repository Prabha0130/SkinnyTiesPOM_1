package com.qa.skinyties.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.skinyties.base.BasePage;
import com.qa.skinyties.util.AppConstant;
import com.qa.skinyties.util.ElementUtils;

public class HomePage extends BasePage {
	WebDriver driver;
	ElementUtils elementUtil;
	String availProduct;
	ProductPage productPage;

	By scrollToContent = By.xpath("//button[@aria-label='Scroll to content']");
	By soldOutProduct = By.xpath("//span[text()='Sold out']/following::a[1]");
	By LastProduct = By.xpath("//*[@id='block-featured-collection-0']/div[1]/div/div[8]/div/div/a/div/img");

	public HomePage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtils(driver);
		productPage = new ProductPage(driver);

	}

	public void VerifyHomePageTitle() {

		elementUtil.VerifyCurrentPageTitle(AppConstant.HOME_PAGE_TITLE, "Home Page");

	}

	public void selectTieInStock() {
		elementUtil.waitUntilElementVisible(scrollToContent, 20);
		elementUtil.clickElement(scrollToContent, "Scroll down arrow for best sellers section");
		ArrayList<String> allProducts = new ArrayList<String>();
		List<WebElement> soldOut = driver.findElements(soldOutProduct);
		elementUtil.waitUntilElementClickable(LastProduct, 30);

		for (int i = 1; i <= AppConstant.HOME_PAGE_PRODUCT_NO; i++) {
			allProducts.add(elementUtil
					.findElement(By
							.xpath("//*[@id='block-featured-collection-0']/div[1]/div/div[" + i + "]/div/div/div/h2/a"))
					.getText());

		}
		System.out.println(allProducts);

		ArrayList<String> soldOutProduct = new ArrayList<String>();
		for (int i = 0; i < soldOut.size(); i++) {
			String temp = soldOut.get(i).getText();
			soldOutProduct.add(temp);
		}

		allProducts.removeAll(soldOutProduct);
		System.out.println("all product size" + allProducts.size());
		BasePage.logger.info("Only " + allProducts.size() + " products are available out of "
				+ AppConstant.HOME_PAGE_PRODUCT_NO + ".",
				MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		availProduct = allProducts.get(0);
		driver.findElement(By.linkText(availProduct)).click();

	}

	public void verifyProductIsSelected() {
		productPage.verifyProductSelected(availProduct);

	}

}
