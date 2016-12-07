package org.ao.suite.test.command.webelement;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
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
			throws RuntimeException {
		
		WebElement webElement = findElement(commandModel.getArgs()[0], suiteDriver);
		webElement.sendKeys(commandModel.getValue().toString());
		
	}

}
