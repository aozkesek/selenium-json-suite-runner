package org.ao.suite.test.command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendKeysCommandDriver extends AbstractCommandDriver {

	public SendKeysCommandDriver(WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(webDriver, commandModel);
		logger = LoggerFactory.getLogger(SendKeysCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		
		WebElement webElement = findElement();
		
	}

}
