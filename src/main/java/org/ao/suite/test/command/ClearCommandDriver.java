package org.ao.suite.test.command;

import org.ao.suite.test.TestContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class ClearCommandDriver extends AbstractCommandDriver {

	public ClearCommandDriver(TestContainer testContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(testContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(ClearCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {}", commandModel);
		WebElement webElement = findElement();
		webElement.clear();
		logger.debug("executed {}", commandModel);
	}

}
