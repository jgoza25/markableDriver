package org.jgoza25.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class MarkableWebElementImpl implements MarkableWebElement {

	private MarkableWebDriver driver;
	private WebElement element;
	
	public MarkableWebElementImpl(MarkableWebDriver driver, WebElement element) {
		this.driver = driver;
		this.element = element;
	}

	public void clear() {
		element.clear();
	}

	public void click() {
		element.click();
	}

	public WebElement findElement(By arg0) {
		return driver.addMarkableElement(element.findElement(arg0));
	}

	public List<WebElement> findElements(By arg0) {
		return driver.addMarkableElement(element.findElements(arg0));
	}

	public String getAttribute(String arg0) {
		return element.getAttribute(arg0);
	}

	public String getCssValue(String arg0) {
		return element.getCssValue(arg0);
	}

	public Point getLocation() {
		return element.getLocation();
	}

	public Dimension getSize() {
		return element.getSize();
	}

	public String getTagName() {
		return element.getTagName();
	}

	public String getText() {
		return element.getText();
	}

	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	public boolean isEnabled() {
		return element.isEnabled();
	}

	public boolean isSelected() {
		return element.isSelected();
	}

	public void sendKeys(CharSequence... arg0) {
		element.sendKeys(arg0);
	}

	public void submit() {
		element.submit();
	}
	
	public void addComment(String comment) {
		driver.addComment(this, comment);
	}
}
