package org.ao.suite.test.command.webelement;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
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
		
		WebElement webElement = findElement(commandModel.getArgs(), suiteDriver);
		commandModel.setValue(webElement.isSelected());
		
	}

}
