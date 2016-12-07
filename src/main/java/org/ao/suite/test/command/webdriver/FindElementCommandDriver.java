package org.ao.suite.test.command.webdriver;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("findElement")
public class FindElementCommandDriver extends AbstractCommandDriver {

	public FindElementCommandDriver() {
		super(LoggerFactory.getLogger(FindElementCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		WebElement webElement = findElement(commandModel.getArgs()[0], suiteDriver);
		commandModel.setValue(webElement);
		
	}

}
