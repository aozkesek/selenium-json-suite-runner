package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("getCssValue")
public class GetCssValueCommandDriver extends AbstractCommandDriver {

	public GetCssValueCommandDriver() {
		super(LoggerFactory.getLogger(GetCssValueCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		if (!commandModel.getArgs().contains(","))
			throw new CommandInvalidArgumentException(commandModel.getArgs());
		
		WebElement webElement = findElement(commandModel.getArgs(), suiteDriver);
		commandModel.setValue(webElement.getCssValue(commandModel.getArgs().split(",")[1]));
		
	}

}
