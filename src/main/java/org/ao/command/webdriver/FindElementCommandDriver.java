package org.ao.command.webdriver;

import org.ao.command.AbstractCommandDriver;
import org.ao.SuiteDriver;
import org.ao.model.CommandModel;
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
		
		WebElement webElement = findElement(commandModel.getArgs().get(0), suiteDriver);
		commandModel.setValue(webElement);
		
	}

}
