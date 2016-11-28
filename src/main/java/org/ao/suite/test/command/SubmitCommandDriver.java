package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class SubmitCommandDriver extends AbstractCommandDriver {

	public SubmitCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(IsSelectedCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		logger.debug("executing {} - {}", getCommand(), getArgs());
		WebElement webElement = findElement();
		webElement.submit();
		logger.debug("executed {} - {}", getCommand(), getArgs());
	}

}
