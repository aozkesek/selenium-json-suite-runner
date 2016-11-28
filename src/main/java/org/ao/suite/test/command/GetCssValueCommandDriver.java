package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class GetCssValueCommandDriver extends AbstractCommandDriver {

	public GetCssValueCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(GetCssValueCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		String args = getArgs();
		logger.debug("executing {} - {}", getCommand(), args);
		
		if (!args.contains(","))
			throw new CommandInvalidArgumentException(args);
		
		WebElement webElement = findElement();
		storeValue(webElement.getCssValue(args.split(",")[1]));
		logger.debug("executed {} - {} - {}", getCommand(), args, getValue());
	}

}
