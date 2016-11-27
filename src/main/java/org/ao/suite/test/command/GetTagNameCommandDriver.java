package org.ao.suite.test.command;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetTagNameCommandDriver extends AbstractCommandDriver {

	public GetTagNameCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(GetTagNameCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		storeValue(webElement.getTagName());
		logger.debug("executed {} - {} - {}", getCommand(), getArgs(), getValue());
	}

}
