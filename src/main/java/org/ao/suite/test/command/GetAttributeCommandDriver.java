package org.ao.suite.test.command;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class GetAttributeCommandDriver extends AbstractCommandDriver {

	public GetAttributeCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(GetAttributeCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		String args = getArgs();
		logger.debug("executing {} - {}", getCommand(), args);
		
		if (!args.contains(","))
			throw new CommandInvalidArgumentException(args);
		
		WebElement webElement = findElement();
		storeValue(webElement.getAttribute(args.split(",")[1]));
		logger.debug("executed {} - {} - {}", getCommand(), args, getValue());
	}

}
