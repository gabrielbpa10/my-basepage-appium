package br.com.appium.mybasepage;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BasePage {

	private AndroidDriver<MobileElement> driver;
	
	public BasePage(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}
	
	protected MobileElement findElement(By by) {
		return this.driver.findElement(by);
	}
	
	protected MobileElement findElementsWaitExplicit(By by, int seconds) {
		WebDriverWait wait = new WebDriverWait(this.driver,seconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		return this.driver.findElement(by);
	}
	
	protected boolean verifyElementDisabled(By by,int seconds, int miliseconds) {
		try {
			Wait espera = new FluentWait(this.driver)
					.withTimeout(seconds, TimeUnit.SECONDS)
					.pollingEvery(miliseconds, TimeUnit.MILLISECONDS)
					.ignoring(NoSuchElementException.class)
					.ignoring(TimeoutException.class);
		
		return (boolean) espera.until(ExpectedConditions.invisibilityOf(this.findElement(by)));
		} catch (Exception e) {
			System.out.println("The element is able, yet.");
			e.getMessage();
			return false;
		}
	}
	
	protected void click(By by) {
		this.findElement(by).click();
	}
	
	protected MobileElement findElementForText(String text) {
		return this.findElement(By.xpath("//*[@text = '" + text + "']"));
	}
	
	protected void clickForText(String text) {
		this.findElementForText(text).click();
	}
	
	protected void writeText(By by, String text) {
		this.findElement(by).sendKeys(text);
	}
	
	protected List<MobileElement> findElements(By by) {
		return this.driver.findElements(by);
	}
	
	protected String returnAttributeAndroid(By by, String attribute) {
		return this.findElement(by).getAttribute(attribute);
	}
	
	protected Boolean verifyText(String text) {
		return this.findElementForText(text).getText() != null ? Boolean.TRUE : Boolean.FALSE;
	}
	
	protected String retornarTextScreen(By by) {
		return this.findElement(by).getText();
	}
	
	protected void waiting(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void scroll(double start, double end) {
		Dimension size = this.driver.manage().window().getSize();
		int x =  size.width/2;
		int y_start = (int) (size.height * start);
		int y_end = (int) (size.height * end);
		
		new TouchAction(this.driver)
		.press(PointOption.point(x, y_start))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
		.moveTo(PointOption.point(x, y_end))
		.release()
		.perform();
		
	}
	
	protected String scrollText(String text) {
		return driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
				+ "new UiSelector().text(\""+text+"\"));")).getText();
	}
	
	protected String scrollTextNoScrollable(String text) {
		return driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView("
				+ "new UiSelector().text(\""+text+"\"));")).getText();
	}
	
	protected String scrollIdView(String text, String id) {
		return driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)" +
				".resourceId(\""+ id +"\"))"+
				".scrollIntoView("+
				 "new UiSelector().text(\""+text+"\"));")).getText();
	}
	
	protected void scrollClick(String text) {
		driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
				+ "new UiSelector().text(\""+text+"\"));")).click();
	}
	
	protected boolean verifyElementAbled(By by) {
		return this.findElement(by).isEnabled();
	}
	
	protected boolean verifyElementAbledWaitExplicit(By by, int seconds) {
		return this.findElementsWaitExplicit(by,seconds).isEnabled();
	}
	
	private void tap(int x, int y) {
		new TouchAction (this.driver).tap(PointOption.point(x, y)).perform();
	}
	
	protected void clickSeekBar(By by, double lenth) {
		MobileElement seek = this.findElement(by);
		int y = seek.getLocation().y + (seek.getSize().height/2);
		int x = (int) (seek.getLocation().x + seek.getSize().width * lenth);
		
		System.out.println("Posição Inicial do X: " + seek.getLocation().x);
		System.out.println("Comprimento da barra: " + seek.getSize().width);
		
		this.tap(x,y);
	}
	
	protected void returnNavegate() {
		this.driver.navigate().back();
	}
}
