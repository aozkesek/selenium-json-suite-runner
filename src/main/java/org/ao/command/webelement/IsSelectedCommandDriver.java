package org.ao.command.webelement;

import org.ao.command.AbstractCommandDriver;
import org.ao.SuiteDriver;
import org.ao.model.CommandModel;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("isSelected")
public class IsSelectedCommandDriver extends AbstractCommandDriver {

	public IsSelectedCommandDriver() {
		super(LoggerFactory.getLogger(IsSelectedCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		WebElement webElement = findElement(commandModel.getArgs().get(0), suiteDriver);
		commandModel.setValue(webElement.isSelected());
		
	}

}
