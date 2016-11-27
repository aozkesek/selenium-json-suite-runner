package org.ao.suite.test.command;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class ClickCommandDriver extends AbstractCommandDriver {

	public ClickCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(ClickCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		webElement.click();
		logger.debug("executed {} - {}", getCommand(), getArgs());
	}

}
