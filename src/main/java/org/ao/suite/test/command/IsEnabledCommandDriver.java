package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("isEnabled")
public class IsEnabledCommandDriver extends AbstractCommandDriver {

	public IsEnabledCommandDriver() {
		super(LoggerFactory.getLogger(IsEnabledCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws ElementNotFoundException {
		
		WebElement webElement = findElement(commandModel.getArgs(), suiteDriver);
		commandModel.setValue(webElement.isEnabled());
		
	}

}
