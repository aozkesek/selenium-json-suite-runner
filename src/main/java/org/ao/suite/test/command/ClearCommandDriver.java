package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("clear")
public class ClearCommandDriver extends AbstractCommandDriver {

	public ClearCommandDriver() {
		super(LoggerFactory.getLogger(ClearCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws ElementNotFoundException {
		
		WebElement webElement = findElement(commandModel.getArgs(), suiteDriver);
		webElement.clear();
		
	}

}
