package org.ao.command;

import java.util.List;

import org.ao.SuiteDriver;
import org.ao.command.exception.ElementNotFoundException;
import org.ao.model.CommandModel;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

@Scope("singleton")
public abstract class AbstractCommandDriver implements ICommandDriver {
	
	protected Logger logger;
	
	public AbstractCommandDriver() {
		this(LoggerFactory.getLogger(AbstractCommandDriver.class));
	}
	
	public AbstractCommandDriver(Logger logger) {
		this.logger = logger;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public String getCommand(CommandModel commandModel) {
		return commandModel.getCommand();
	}
	
	protected WebElement findElement(String args, SuiteDriver suiteDriver) throws ElementNotFoundException {
		
		logger.debug("find element for {}", args);
		
		By by = getBy(args);
		if (by == null)
			throw new ElementNotFoundException(args);
		
		WebElement webElement = suiteDriver.findElementBy(by);
		
		return webElement;
		
	}
	
	protected List<WebElement> findElements(String args, SuiteDriver suiteDriver) throws ElementNotFoundException {
		
		logger.debug("find element for {}", args);
		
		By by = getBy(args);
		if (by == null)
			throw new ElementNotFoundException(args);
		
		List<WebElement> webElements = suiteDriver.findElementsBy(by);

		return webElements;
		
	}
	
	private By getBy(String args) {
		
		String element = args;
		
		if (element.contains(","))
			element = element.split(",")[0];
					
		if (element.startsWith("id="))
			return new ById(element.substring(3));
		else if (element.startsWith("name="))
			return new ByName(element.substring(5));
		else if (element.startsWith("className="))
			return new ByClassName(element.substring(10));
		else if (element.startsWith("cssSelector="))
			return new ByCssSelector(element.substring(10));
		else if (element.startsWith("tagName="))
			return new ByTagName(element.substring(8));
		else if (element.startsWith("xPath="))
			return new ByXPath(element.substring(6));
		else if (element.startsWith("//"))
			return new ByXPath(element);
		else if (element.startsWith("linkText="))
			return new ByLinkText(element.substring(9));
		else if (element.startsWith("partialLinkText="))
			return new ByPartialLinkText(element.substring(16));
		
		return null;
		
	}
	
	

}
