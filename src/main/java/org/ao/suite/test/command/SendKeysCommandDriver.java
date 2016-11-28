package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class SendKeysCommandDriver extends AbstractCommandDriver {

	public SendKeysCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(SendKeysCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {	
		super.execute();
		logger.debug("executing {} - {} - {}", getCommand(), getArgs(), getValue());
		WebElement webElement = findElement();
		webElement.sendKeys(getValue());
		logger.debug("executed {} - {}", getCommand(), getArgs());
	}

}
