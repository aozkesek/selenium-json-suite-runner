package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("click")
public class ClickCommandDriver extends AbstractCommandDriver {
	
	public ClickCommandDriver() {
		super(LoggerFactory.getLogger(ClickCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		WebElement webElement = findElement(commandModel.getArgs(), suiteDriver);
		webElement.click();
		
	}

}
