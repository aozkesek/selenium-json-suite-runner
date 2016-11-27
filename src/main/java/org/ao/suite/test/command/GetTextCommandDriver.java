package org.ao.suite.test.command;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class GetTextCommandDriver extends AbstractCommandDriver {

	public GetTextCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(GetTextCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		super.execute();
		logger.debug("executing {}", commandModel);
		WebElement webElement = findElement();
		commandModel.setValue(webElement.getText());
		logger.debug("executed {}", commandModel);
	}

}
