package org.jgoza25.selenium.example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jgoza25.selenium.MarkableWebDriver;
import org.jgoza25.selenium.MarkableWebElementImpl;
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

		driver.get("https://github.com/jgoza25/markableDriver");
		assertThat(driver.getTitle(), is("jgoza25/markableDriver · GitHub"));
		assertThat(driver.findElement(By.linkText("Explore GitHub")).isDisplayed(), is(true));
		assertThat(driver.findElement(By.linkText("Search")).isDisplayed(), is(true));
		assertThat(driver.findElement(By.linkText("Features")).isDisplayed(), is(true));
		assertThat(driver.findElement(By.linkText("Blog")).isDisplayed(), is(true));
		WebElement urlbox = driver.findElement(By.cssSelector("div.url-box.js-url-box"));
		WebElement urlField = urlbox.findElement(By.cssSelector("input.url-field.js-url-field"));

		assertThat(urlField.getAttribute("value"), is("https://github.com/jgoza25/markableDriver.git"));
		WebElement srchlink = driver.findElement(By.linkText("Search"));

		// div.frame-meta 要素をマスクします。
		WebElement co = driver.findElement(By.cssSelector("div.frame-meta"));
		mask(co);
		
		// Searchリンク要素を囲みclickとコメントを記載します。
		comment(srchlink, "click");
		capture(driver, "res/01.png");
		srchlink.click();

		WebElement srchtxt = driver.findElement(By.name("q"));
		srchtxt.sendKeys("selenium");
		WebElement srchbtn = driver.findElement(By.cssSelector("button.button"));
		comment(srchbtn, "click");

		capture(driver, "res/02.png");
		srchbtn.click();

		assertEquals("selenium", driver.findElement(By.name("q")).getAttribute("value"));
		capture(driver, "res/03.png");

		driver.close();
	}

	protected void capture(WebDriver driver, String path) throws IOException {
		if (driver instanceof TakesScreenshot) {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(path));
		}
	}

	protected void comment(WebElement element, String comment) {
		if (element instanceof MarkableWebElementImpl) {
			((MarkableWebElementImpl) element).addComment(comment);
		}
	}
	
	protected void mask(WebElement element) {
		if (element instanceof MarkableWebElementImpl) {
			((MarkableWebElementImpl) element).mask();
		}
	}
}
