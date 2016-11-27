package org.ao.suite.test.command;

import java.util.List;

import org.ao.suite.ObjectContainer;
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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommandDriver implements ICommandDriver {
	
	protected Logger logger = LoggerFactory.getLogger(AbstractCommandDriver.class);
	
	private CommandModel commandModel;
	protected WebDriver webDriver;
	protected ObjectContainer objectContainer;	
	
	public AbstractCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) {
		this.objectContainer = objectContainer;
		this.commandModel = commandModel;
		this.webDriver = webDriver;

	}
	
	@Override
	public void execute() throws ElementNotFoundException {
		
	}
	
	protected Logger getLogger() {
		return logger;
	}
	
	protected String getCommand() {
		return commandModel.getCommand();
	}
	
	protected String getArgs() {
		String args = commandModel.getArgs();
		if (objectContainer.containsVariable(args))
			args = objectContainer.replaceVariables(args);
		return args;
	}
	
	protected String getValue() {
		String value = commandModel.getValue();
		if (objectContainer.containsVariable(value))
			value = objectContainer.replaceVariables(value);
		return value;
	}
	
	protected WebElement findElement() throws ElementNotFoundException {
		
		getLogger().debug("find element for {}", commandModel);
		
		By by = getBy();
		if (by == null)
			throw new ElementNotFoundException(commandModel.getArgs());
		
		new WebDriverWait(webDriver, 10000).until(
				new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver d) {
						return d.findElement(by);
					}
				});
			
		WebElement webElement = webDriver.findElement(by);
		
		return webElement;
		
	}
	
	protected List<WebElement> findElements() throws ElementNotFoundException {
		
		getLogger().debug("find element for {}", commandModel);
		
		By by = getBy();
		if (by == null)
			throw new ElementNotFoundException(commandModel.getArgs());
			
		new WebDriverWait(webDriver, 10000).until(
				new ExpectedCondition<List<WebElement>>() {
					public List<WebElement> apply(WebDriver d) {
						return d.findElements(by);
					}
				});
		
		List<WebElement> webElements = webDriver.findElements(by);

		return webElements;
		
	}
	
	protected void storeValue(Object value) {
		//to-do: check value if it is variable or not, then update/replace 
		
		//for a while put directly 
		commandModel.setValue(String.valueOf(value));
		
		//to-do: update the variable value in the object container
		
	}
	
	protected By getBy() {
		
		String args = getArgs();
		
		if (args.contains(","))
			args = args.split(",")[0];
					
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
