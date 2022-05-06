package org.ao.command.webelement;

import org.ao.command.AbstractCommandDriver;
import org.ao.SuiteDriver;
import org.ao.model.CommandModel;
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
			throws RuntimeException {
		
		WebElement webElement = findElement(commandModel.getArgs().get(0), suiteDriver);
		commandModel.setValue(webElement.isEnabled());
		
	}

}
