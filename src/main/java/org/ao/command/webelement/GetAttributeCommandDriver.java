package org.ao.command.webelement;

import org.ao.command.AbstractCommandDriver;
import org.ao.SuiteDriver;
import org.ao.command.exception.CommandInvalidArgumentException;
import org.ao.model.CommandModel;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("getAttribute")
public class GetAttributeCommandDriver extends AbstractCommandDriver {
	
	public GetAttributeCommandDriver() {
		super(LoggerFactory.getLogger(GetAttributeCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		if (commandModel.getArgs() == null || commandModel.getArgs().size() < 2)
			throw new CommandInvalidArgumentException(commandModel.getCommand());
		
		WebElement webElement = findElement(commandModel.getArgs().get(0), suiteDriver);
		commandModel.setValue(webElement.getAttribute(commandModel.getArgs().get(1)));
	}

}
