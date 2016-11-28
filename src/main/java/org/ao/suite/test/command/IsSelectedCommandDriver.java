package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class IsSelectedCommandDriver extends AbstractCommandDriver {

	public IsSelectedCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(IsSelectedCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		storeValue(String.valueOf(webElement.isSelected()));
		logger.debug("executed {} - {} - {}", getCommand(), getArgs(), getValue());
	}

}
