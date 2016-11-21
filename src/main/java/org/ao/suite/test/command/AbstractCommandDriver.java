package org.ao.suite.test.command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

public abstract class AbstractCommandDriver implements ICommandDriver {
	
	protected CommandModel commandModel;
	protected WebDriver webDriver;
	
	public AbstractCommandDriver(WebDriver webDriver, CommandModel commandModel) {
		this.commandModel = commandModel;
		this.webDriver = webDriver;

	}
	
	protected abstract Logger getLogger();
	
	protected WebElement findElement() throws ElementNotFoundException {
		
		
		
		throw new ElementNotFoundException(commandModel.getArgs());
		
	}

}
