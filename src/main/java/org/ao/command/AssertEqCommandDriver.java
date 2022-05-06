package org.ao.command;

import org.ao.command.exception.AssertEqException;
import org.ao.command.exception.CommandInvalidArgumentException;
import org.ao.command.exception.ElementNotFoundException;
import org.ao.model.CommandModel;
import org.ao.SuiteDriver;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("assertEq")
public class AssertEqCommandDriver extends AbstractCommandDriver {
	
	public AssertEqCommandDriver() {
		super(LoggerFactory.getLogger(AssertEqCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver)
			throws ElementNotFoundException {
		
		if (commandModel.getArgs() == null || commandModel.getArgs().size() < 2)
			throw new CommandInvalidArgumentException(commandModel.getCommand());
		
		String actual = commandModel.getArgs().get(0);
		String expected = commandModel.getArgs().get(1);
		
		if (actual.equals(expected))
			throw new AssertEqException(actual + " equal to " + expected);
		
	}

}
