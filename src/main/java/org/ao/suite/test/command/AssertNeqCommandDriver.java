package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
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
		
		String args = commandModel.getArgs();

		if (!args.contains(","))
			throw new CommandInvalidArgumentException(args);
		
		String actual = args.split(",")[0];
		String expected = args.split(",")[1];
		
		if (!actual.equals(expected))
			return;
		
		throw new AssertEqException(actual + " equal to " + expected);
		
	}

}
