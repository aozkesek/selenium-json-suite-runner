package org.ao.suite.test.command;

import java.util.List;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class FindElementsCommandDriver extends AbstractCommandDriver {

	public FindElementsCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(FindElementsCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {}", commandModel);
		List<WebElement> webElements = findElements();
		storeValue(webElements);
		logger.debug("executed {}", commandModel);
		
	}

}
