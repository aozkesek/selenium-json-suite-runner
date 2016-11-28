package org.ao.suite.test.command;

import java.util.List;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class FindElementsCommandDriver extends AbstractCommandDriver {

	public FindElementsCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(suiteDriver, commandModel);
		logger = LoggerFactory.getLogger(FindElementsCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		logger.debug("executing {} - {}", getCommand(), getArgs());
		List<WebElement> webElements = findElements();
		storeValue(webElements);
		logger.debug("executed {} - {} - {}", getCommand(), getArgs(), getValue());
		
	}

}
