package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class IsDisplayedCommandDriver extends AbstractCommandDriver {

	public IsDisplayedCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(IsDisplayedCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		storeValue(String.valueOf(webElement.isDisplayed()));
		logger.debug("executed {} - {} - {}", getCommand(), getArgs(), getValue());
	}

}
