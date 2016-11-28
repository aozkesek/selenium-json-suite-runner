package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class FindElementCommandDriver extends AbstractCommandDriver {

	public FindElementCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(FindElementCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		storeValue(webElement);
		logger.debug("executed {} - {} - {}", getCommand(), getArgs(), getValue());
		
	}

}
