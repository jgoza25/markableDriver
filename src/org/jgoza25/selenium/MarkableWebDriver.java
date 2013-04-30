package org.jgoza25.selenium;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class MarkableWebDriver implements WebDriver, TakesScreenshot {

	private WebDriver driver;
	private Map<WebElement, String> elements = new LinkedHashMap<WebElement, String>();

	public MarkableWebDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement addMarkableElement(WebElement element) {
		this.elements.put(element, null);
		return new MarkableWebElement(this, element);
	}
	
	public List<WebElement> addMarkableElement(List<WebElement> elements) {
		List<WebElement> markableList = new ArrayList<WebElement>();
		for (WebElement element : elements) {
			markableList.add(addMarkableElement(element));
		}
		return markableList;
	}

	public void addComment(WebElement element, String comment) {
		this.elements.put(element, comment);
	}
	
	public void close() {
		driver.close();
	}

	public WebElement findElement(By arg0) {
		WebElement element = driver.findElement(arg0);
		return addMarkableElement(element);
	}

	public List<WebElement> findElements(By arg0) {
		List<WebElement> list2 = new ArrayList<WebElement>();
		List<WebElement> list = driver.findElements(arg0);
		for (WebElement element : list) {
			addMarkableElement(element);
			list2.add(new MarkableWebElement(this, element));
		}
		return list;
	}

	public void get(String arg0) {
		driver.get(arg0);
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public Options manage() {
		return driver.manage();
	}

	public Navigation navigate() {
		return driver.navigate();
	}

	public void quit() {
		driver.quit();
	}

	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> type) throws WebDriverException {
		if (!(driver instanceof TakesScreenshot)) {
			return null;
		}
		TakesScreenshot ts = (TakesScreenshot) driver;
		X x = ts.getScreenshotAs(type);
		try {
			BufferedImage img = getImage(type, x);
			Graphics g = img.getGraphics();
			float dash[] = { 4.0f, 2.0f };
			((Graphics2D) g).setStroke(new BasicStroke(2.0f,
					BasicStroke.JOIN_ROUND, BasicStroke.CAP_BUTT, 1.0f,
					dash, 0.0f));
			for (WebElement element : elements.keySet()) {
				try {
					g.setColor(Color.RED);
					g.drawRoundRect(element.getLocation().x - 5,
							element.getLocation().y - 5,
							element.getSize().width + 10,
							element.getSize().height + 10, 10, 10);
					if (elements.get(element) != null) {
						int width = g.getFontMetrics().stringWidth(elements.get(element));
						int height = g.getFontMetrics().getHeight();
						g.setColor(Color.WHITE);
						g.fillRect(element.getLocation().x, element.getLocation().y - 10 - height, width, height);
						g.setColor(Color.RED);
						g.drawString(elements.get(element), element.getLocation().x, element.getLocation().y - 10);
					}
				} catch (StaleElementReferenceException e) {
					// ignore
				}
			}
			g.dispose();
			elements.clear();
			return (X) writeImage(type, img);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <X> BufferedImage getImage(OutputType<X> type, Object x)
			throws IOException {
		if (type == OutputType.BYTES) {
			return ImageIO.read(new ByteArrayInputStream((byte[]) x));
		}
		if (type == OutputType.FILE) {
			return ImageIO.read((File) x);
		}
		if (type == OutputType.BASE64) {
			return ImageIO.read(new ByteArrayInputStream(Base64
					.decode((String) x)));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private <X> X writeImage(OutputType<X> type, BufferedImage img)
			throws IOException {
		File tmpfile = File.createTempFile("MarkableWebDriver", ".png");
		ImageIO.write(img, "PNG", tmpfile);
		if (type == OutputType.BYTES) {
			return (X) IOUtils.toByteArray(new FileInputStream(tmpfile));
		}
		if (type == OutputType.FILE) {
			return (X) tmpfile;
		}
		if (type == OutputType.BASE64) {
			return (X) Base64.encode(IOUtils.toByteArray(new FileInputStream(
					tmpfile)));
		}
		return null;
	}
}
