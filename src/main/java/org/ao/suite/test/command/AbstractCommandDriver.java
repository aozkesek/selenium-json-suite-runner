package org.ao.suite.test.command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommandDriver implements ICommandDriver {
	
	protected Logger logger = LoggerFactory.getLogger(AbstractCommandDriver.class);
	
	protected CommandModel commandModel;
	protected WebDriver webDriver;
	
	public AbstractCommandDriver(WebDriver webDriver, CommandModel commandModel) {
		this.commandModel = commandModel;
		this.webDriver = webDriver;

	}
	
	protected Logger getLogger() {
		return logger;
	}
	
	protected WebElement findElement() throws ElementNotFoundException {
		
		getLogger().debug("find element for {}", commandModel);
		
		throw new ElementNotFoundException(commandModel.getArgs());
		
	}

}
