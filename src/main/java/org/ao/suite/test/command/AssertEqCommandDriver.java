package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
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
		
		String args = commandModel.getArgs();

		if (!args.contains(","))
			throw new CommandInvalidArgumentException(args);
		
		String actual = args.split(",")[0];
		String expected = args.split(",")[1];
		
		if (actual.equals(expected))
			throw new AssertEqException(actual + " equal to " + expected);
		
	}

}
