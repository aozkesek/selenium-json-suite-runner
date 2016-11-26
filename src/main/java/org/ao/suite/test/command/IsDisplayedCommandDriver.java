package org.ao.suite.test.command;

import org.ao.suite.test.TestContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class IsDisplayedCommandDriver extends AbstractCommandDriver {

	public IsDisplayedCommandDriver(TestContainer testContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(testContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(IsDisplayedCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {}", commandModel);
		WebElement webElement = findElement();
		commandModel.setValue(String.valueOf(webElement.isDisplayed()));
		logger.debug("executed {}", commandModel);
	}

}
