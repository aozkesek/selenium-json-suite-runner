package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class ClearCommandDriver extends AbstractCommandDriver {

	public ClearCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(ClearCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		webElement.clear();
		logger.debug("executed {} - {}", getCommand(), getArgs());
	}

}
