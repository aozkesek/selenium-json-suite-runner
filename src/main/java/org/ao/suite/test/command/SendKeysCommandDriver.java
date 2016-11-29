package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("sendKeys")
public class SendKeysCommandDriver extends AbstractCommandDriver {

	public SendKeysCommandDriver() {
		super(LoggerFactory.getLogger(SendKeysCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws ElementNotFoundException {
		
		WebElement webElement = findElement(commandModel.getArgs(), suiteDriver);
		webElement.sendKeys(commandModel.getValue().toString());
		
	}

}
