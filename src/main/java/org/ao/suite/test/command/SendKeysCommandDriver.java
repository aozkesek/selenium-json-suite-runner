package org.ao.suite.test.command;

import org.ao.suite.test.TestContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

public class SendKeysCommandDriver extends AbstractCommandDriver {

	public SendKeysCommandDriver(TestContainer testContainer, WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(testContainer, webDriver, commandModel);
		logger = LoggerFactory.getLogger(SendKeysCommandDriver.class);
	}

	@Override
	public void execute() throws ElementNotFoundException {
		
		WebElement webElement = findElement();
		
		String value = commandModel.getValue();
		if (containsVariable(value))
			value = replaceVariables(value);
		
		webElement.sendKeys(commandModel.getValue());
	}

}
