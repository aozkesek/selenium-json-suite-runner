package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class ClickCommandDriver extends AbstractCommandDriver {

	public ClickCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
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
