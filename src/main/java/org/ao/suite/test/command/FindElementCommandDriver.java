package org.ao.suite.test.command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindElementCommandDriver extends AbstractCommandDriver {

	public FindElementCommandDriver(WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(webDriver, commandModel);
		logger = LoggerFactory.getLogger(FindElementCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		
		WebElement webElement = findElement();
		
	}

}
