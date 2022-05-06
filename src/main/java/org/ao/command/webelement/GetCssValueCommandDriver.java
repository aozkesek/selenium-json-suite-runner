package org.ao.command.webelement;

import org.ao.command.AbstractCommandDriver;
import org.ao.SuiteDriver;
import org.ao.command.exception.CommandInvalidArgumentException;
import org.ao.model.CommandModel;
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
		
		if (commandModel.getArgs() == null || commandModel.getArgs().size() < 2)
			throw new CommandInvalidArgumentException(commandModel.getCommand());
		
		WebElement webElement = findElement(commandModel.getArgs().get(0), suiteDriver);
		commandModel.setValue(webElement.getCssValue(commandModel.getArgs().get(1)));
		
	}

}
