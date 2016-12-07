package org.ao.suite.test.command.base;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.exception.AssertEqException;
import org.ao.suite.test.command.exception.CommandInvalidArgumentException;
import org.ao.suite.test.command.exception.ElementNotFoundException;
import org.ao.suite.test.command.model.CommandModel;
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
		
		if (commandModel.getArgs() == null || commandModel.getArgs().length < 2)
			throw new CommandInvalidArgumentException(commandModel.getCommand());
		
		String actual = commandModel.getArgs()[0];
		String expected = commandModel.getArgs()[1];
		
		if (actual.equals(expected))
			throw new AssertEqException(actual + " equal to " + expected);
		
	}

}
