package org.ao.suite.test.command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClearCommandDriver extends AbstractCommandDriver {

	public ClearCommandDriver(WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(webDriver, commandModel);
		logger = LoggerFactory.getLogger(ClearCommandDriver.class);
		
	}

	@Override
	public void execute() throws ElementNotFoundException {
		
		WebElement webElement = findElement();
		
	}

}
