package org.ao.suite.test.command;

import java.util.List;

import org.ao.suite.test.TestContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommandDriver implements ICommandDriver {
	
	protected Logger logger = LoggerFactory.getLogger(AbstractCommandDriver.class);
	
	protected CommandModel commandModel;
	protected WebDriver webDriver;
	protected TestContainer testContainer;	
	
	public AbstractCommandDriver(TestContainer testContainer, WebDriver webDriver, CommandModel commandModel) {
		this.testContainer = testContainer;
		this.commandModel = commandModel;
		this.webDriver = webDriver;

	}
	
	protected Logger getLogger() {
		return logger;
	}
	
	protected WebElement findElement() throws ElementNotFoundException {
		
		getLogger().debug("find element for {}", commandModel);
		
		By by = parseArguments();
		if (by == null)
			throw new ElementNotFoundException(commandModel.getArgs());
			
		WebElement webElement = webDriver.findElement(by);
		
		return webElement;
		
	}
	
	protected List<WebElement> findElements() throws ElementNotFoundException {
		
		getLogger().debug("find element for {}", commandModel);
		
		By by = parseArguments();
		if (by == null)
			throw new ElementNotFoundException(commandModel.getArgs());
			
		List<WebElement> webElements = webDriver.findElements(by);
		
		return webElements;
		
	}
	
	protected void storeValue(Object value) {
		
		
		
	}
	
	protected By parseArguments() {
		
		String args = commandModel.getArgs();
		
		/* first: check here whether argument is variable or not
		 *   if variable: get current value from the variable container
		 */
		
		
		if (args.startsWith("id="))
			return new ById(args.substring(3));
		else if (args.startsWith("name="))
			return new ByName(args.substring(5));
		else if (args.startsWith("className="))
			return new ByClassName(args.substring(10));
		else if (args.startsWith("cssSelector="))
			return new ByCssSelector(args.substring(10));
		else if (args.startsWith("tagName="))
			return new ByTagName(args.substring(8));
		else if (args.startsWith("xPath="))
			return new ByXPath(args.substring(6));
		else if (args.startsWith("//"))
			return new ByXPath(args);
		else if (args.startsWith("linkText="))
			return new ByLinkText(args.substring(9));
		else if (args.startsWith("partialLinkText="))
			return new ByPartialLinkText(args.substring(16));
		
		return null;
		
	}
	
	

}
