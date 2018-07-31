package org.ao.suite.test.command;

import java.util.List;

import org.ao.suite.ObjectContainer;
import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.exception.ElementNotFoundException;
import org.ao.suite.test.command.model.CommandModel;
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

@Scope("prototype")
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
	
	public String[] getArgs(CommandModel commandModel, ObjectContainer objectContainer) {
		String[] args = commandModel.getArgs();
		for (int i = 0; i < args.length; i++)
			if (objectContainer.containsVariable(args[i]))
				args[i] = objectContainer.replaceVariables(args[i]);
		return args;
	}
	
	public String getValue(CommandModel commandModel, ObjectContainer objectContainer) {
		String value = String.valueOf(commandModel.getValue());
		if (objectContainer.containsVariable(value))
			value = objectContainer.replaceVariables(value);
		return value;
	}
	
	protected WebElement findElement(String args, SuiteDriver suiteDriver) throws ElementNotFoundException {
		
		logger.debug("find element for {}", args);
		
		By by = getBy(args);
		if (by == null)
			throw new ElementNotFoundException(args);
		
		WebElement webElement = suiteDriver.getWebDriver().findElement(by);
		
		return webElement;
		
	}
	
	protected List<WebElement> findElements(String args, SuiteDriver suiteDriver) throws ElementNotFoundException {
		
		logger.debug("find element for {}", args);
		
		By by = getBy(args);
		if (by == null)
			throw new ElementNotFoundException(args);
		
		List<WebElement> webElements = suiteDriver.getWebDriver().findElements(by);

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
