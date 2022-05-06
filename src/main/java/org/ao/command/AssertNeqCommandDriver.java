package org.ao.command;

import org.ao.command.exception.AssertNeqException;
import org.ao.command.exception.CommandInvalidArgumentException;
import org.ao.command.exception.ElementNotFoundException;
import org.ao.SuiteDriver;
import org.ao.model.CommandModel;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("assertNeq")
public class AssertNeqCommandDriver extends AbstractCommandDriver {
	
	public AssertNeqCommandDriver() {
		super(LoggerFactory.getLogger(AssertNeqCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws ElementNotFoundException {
		
		if (commandModel.getArgs() == null || commandModel.getArgs().size() < 2)
			throw new CommandInvalidArgumentException(commandModel.getCommand());
		
		String actual = commandModel.getArgs().get(0);
		String expected = commandModel.getArgs().get(1);
		
		if (!actual.equals(expected))
			throw new AssertNeqException(actual + " not equal to " + expected);
		
	}

}
