package org.ao.suite.test.command;

import org.ao.suite.ObjectContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class SendKeysCommandDriver extends AbstractCommandDriver {

	public SendKeysCommandDriver(ObjectContainer objectContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(objectContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(SendKeysCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {	
		super.execute();
		logger.debug("executing {}", commandModel);
		WebElement webElement = findElement();
		webElement.sendKeys(this.commandModel.getValue());
		logger.debug("executed {}", commandModel);
	}

}
