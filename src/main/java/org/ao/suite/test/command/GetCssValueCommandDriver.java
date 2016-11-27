package org.ao.suite.test.command;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class GetCssValueCommandDriver extends AbstractCommandDriver {

	public GetCssValueCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(GetCssValueCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {}", commandModel);
		
		if (!commandModel.getArgs().contains(","))
			throw new CommandInvalidArgumentException(commandModel.getArgs());
		
		WebElement webElement = findElement();
		commandModel.setValue(webElement.getCssValue(commandModel.getArgs().split(",")[1]));
		logger.debug("executed {}", commandModel);
	}

}
