package org.jgoza25.selenium;

import org.openqa.selenium.WebElement;

public interface MarkableWebElement extends WebElement {
	void addComment(String comment);
	void mask();
}
