package org.ao.suite.test.command;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class IsEnabledCommandDriver extends AbstractCommandDriver {

	public IsEnabledCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(IsEnabledCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		//commandModel.setValue(String.valueOf(webElement.isEnabled()));
		logger.debug("executied {} - {} - {}", getCommand(), getArgs(), getValue());
	}

}
