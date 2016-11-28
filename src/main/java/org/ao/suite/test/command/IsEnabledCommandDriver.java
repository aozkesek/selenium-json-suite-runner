package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class IsEnabledCommandDriver extends AbstractCommandDriver {

	public IsEnabledCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(IsEnabledCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		storeValue(String.valueOf(webElement.isEnabled()));
		logger.debug("executied {} - {} - {}", getCommand(), getArgs(), getValue());
	}

}
