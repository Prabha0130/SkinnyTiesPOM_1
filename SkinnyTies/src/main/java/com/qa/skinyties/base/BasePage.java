package com.qa.skinyties.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BasePage {
	
	public Properties prop;
	public static ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static DateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
	public static Date date = new Date();
	
	private static ThreadLocal<WebDriver> driver=new ThreadLocal<WebDriver>();
	
	public static synchronized WebDriver getDriver() {
		return driver.get();
	}

	

	public WebDriver initializeBrowser(String browser){
		
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "./resource/drivers/chromedriver.exe");
			driver.set(new ChromeDriver());
		}
		else if(browser.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", "./resource/drivers/geckodriver.exe");
			driver.set(new FirefoxDriver());
		}
		else{
			System.out.println("Browser name is wrong");
		}
		driver.get().manage().window().maximize();
		driver.get().manage().deleteAllCookies();
		return driver.get();
	}
	
	public Properties initializePropertyFile(){
		prop=new Properties();
		FileInputStream file;
		
		try {
			file = new FileInputStream(System.getProperty("user.dir")+"./src/main/java/com/qa/skinyties/config/config.properties");
			prop.load(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	
	public String takeScreenShot(){
		String uniqueID = UUID.randomUUID().toString();
		System.out.println(uniqueID);
		File src=((TakesScreenshot)driver.get()).getScreenshotAs(OutputType.FILE);
		String path= System.getProperty("user.dir")+"\\resource\\screenshot\\Screenshot_"+uniqueID+".png";
		File destination=new File(path);
			try {
				FileUtils.copyFile(src, destination);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Screenshot path:"+path);
		return path;
	}

	

}
