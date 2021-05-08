package com.qa.skinyties.pages;

import org.jsoup.Connection.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.base.Throwables;
import com.qa.skinyties.base.BasePage;
import com.qa.skinyties.util.AppConstant;
import com.qa.skinyties.util.ElementUtils;

public class ProductPage extends BasePage {

	WebDriver driver;
	ElementUtils elementUtil;
	String availProduct;
	String quantity;

	By productDesc = By.cssSelector(".ProductMeta__Title.Heading.u-h2");
	By defaultOrderQuantity = By.xpath("//input[@name='quantity']");
	By addToCartBttn = By.xpath("//span[text()='Add to cart']");
	By cartBttn = By.xpath("//a[@aria-label][@class='Heading u-h6']");
	By closeCartBttn = By.xpath("//button[@aria-label='Close cart']");
	By yourCartIsEmptyTxt = By.cssSelector(".Cart__Empty.Heading.u-h5");
	By cartQuantity = By.xpath("//input[@name='updates[]']");
	By removeItemInCart = By.xpath("//a[@data-action='remove-item']");

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtils(driver);

	}

	public void verifyProductSelected(String productName) {
		elementUtil.waitUntilElementVisible(productDesc, 30);
		String productDescription = elementUtil.getText(productDesc);
		try {
			if (productDescription.equals(productName)) {
				BasePage.logger.pass(productName + " is selected successfully and navigated to Product page",
						MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());

			} else {
				throw new Exception("Product page navigation failed");
			}
		} catch (Exception e) {
			BasePage.logger.fail("Unable to select " + productName + " from homepage",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			e.printStackTrace();
			Assert.fail();
		}
	}

	public void openCart() {

		elementUtil.waitUntilElementVisible(cartBttn, 20);
		elementUtil.clickElement(cartBttn, "Cart button");
	}

	public void verifyCartIsEmpty() {
		BasePage.logger.info("Verify Cart is empty");
		elementUtil.waitUntilElementVisible(yourCartIsEmptyTxt, 30);
		String cartIsEmptyMsg = elementUtil.getText(yourCartIsEmptyTxt);
		try {
			elementUtil.waitUntilElementVisible(closeCartBttn, 20);
			if (cartIsEmptyMsg.equals(AppConstant.EMPTY_CART_MESSAGE)) {
				BasePage.logger.pass("Cart is empty successfully verified",
						MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());

			} else {
				throw new Exception("Cart is empty ");
			}
		} catch (Exception e) {
			BasePage.logger.fail("Cart is not empty message is not captured");
			e.printStackTrace();
		}
		elementUtil.clickElement(closeCartBttn, "Close cart button");
	}

	public void addTieToCart() {

		// driver.navigate().refresh();
		elementUtil.waitUntilElementVisible(defaultOrderQuantity, 30);
		quantity = elementUtil.getAttribute(defaultOrderQuantity, "value");
		System.out.println(quantity);
		elementUtil.clickElement(addToCartBttn, "Add to cart button with "+quantity+" quantity");

	}

	public void verifyCartIsUpdated() {
		elementUtil.waitUntilElementVisible(cartQuantity, 30);
		String cartQty = elementUtil.getAttribute(cartQuantity, "value");
		try{
		if (cartQty.equals(AppConstant.DEFAULT_ORDER_QUANTITY)) {
			BasePage.logger.pass("Add to cart is successfull, cart quantity is increase to " + cartQty+".",
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
		} else {
			throw new Exception("Add to cart is failed");

		}}catch (Exception e) {
			BasePage.logger.fail("Add to cart is failed, cart quantity is " + cartQty,
					MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot()).build());
			e.printStackTrace();
		}

	}

	public void removeTieFromCart() {
		elementUtil.waitUntilElementVisible(removeItemInCart, 20);
		elementUtil.clickElement(removeItemInCart, "Remove link in cart");
		verifyCartIsEmpty();

	}

}
