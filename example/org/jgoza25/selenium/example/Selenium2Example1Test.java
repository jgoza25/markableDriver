package org.jgoza25.selenium.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jgoza25.selenium.MarkableWebDriver;
import org.jgoza25.selenium.MarkableWebElement;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Selenium2Example1Test {

	@Test
	public void test() throws Exception {

		// WebDriver driver = new FirefoxDriver();
		WebDriver driver = new MarkableWebDriver(new FirefoxDriver());
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

		driver.get("https://github.com/jgoza25/Selenium.MarkableWebDriver");
		assertThat(driver.getTitle(), is("jgoza25/Selenium.MarkableWebDriver · GitHub"));
		assertThat(driver.findElement(By.linkText("Explore GitHub")).isDisplayed(), is(true));
		assertThat(driver.findElement(By.linkText("Search")).isDisplayed(), is(true));
		assertThat(driver.findElement(By.linkText("Features")).isDisplayed(), is(true));
		assertThat(driver.findElement(By.linkText("Blog")).isDisplayed(), is(true));
		WebElement urlbox = driver.findElement(By.cssSelector("div.url-box.js-url-box"));
		WebElement urlField = urlbox.findElement(By.cssSelector("input.url-field.js-url-field"));

		assertThat(urlField.getAttribute("value"), is("https://github.com/jgoza25/Selenium.MarkableWebDriver.git"));
		WebElement srchlink = driver.findElement(By.linkText("Search"));

		comment(srchlink, "click");
		capture(driver, "01b.png");
		srchlink.click();

		WebElement srchtxt = driver.findElement(By.name("q"));
		srchtxt.sendKeys("selenium");
		WebElement srchbtn = driver.findElement(By.cssSelector("button.button"));
		comment(srchbtn, "click");

		capture(driver, "02b.png");
		srchbtn.click();

		assertEquals("selenium", driver.findElement(By.name("q")).getAttribute("value"));
		capture(driver, "03b.png");

		driver.close();
	}

	protected void capture(WebDriver driver, String path) throws IOException {
		if (driver instanceof TakesScreenshot) {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(path));
		}
	}

	protected void comment(WebElement element, String comment) {
		if (element instanceof MarkableWebElement) {
			((MarkableWebElement) element).addComment(comment);
		}
	}
}
