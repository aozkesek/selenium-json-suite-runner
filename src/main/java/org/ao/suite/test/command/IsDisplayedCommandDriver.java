package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("isDisplayed")
public class IsDisplayedCommandDriver extends AbstractCommandDriver {
	
	public IsDisplayedCommandDriver() {
		super(LoggerFactory.getLogger(IsDisplayedCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		WebElement webElement = findElement(commandModel.getArgs(), suiteDriver);
		commandModel.setValue(webElement.isDisplayed());
		
	}

}
