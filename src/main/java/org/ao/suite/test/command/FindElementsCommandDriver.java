package org.ao.suite.test.command;

import java.util.List;

import org.ao.suite.test.TestContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class FindElementsCommandDriver extends AbstractCommandDriver {

	public FindElementsCommandDriver(TestContainer testContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(testContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(FindElementsCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		
		List<WebElement> webElements = findElements();
		storeValue(webElements);
		logger.debug("executed {}", commandModel);
		
	}

}
