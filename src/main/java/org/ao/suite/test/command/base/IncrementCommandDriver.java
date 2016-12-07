package org.ao.suite.test.command.base;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("inc")
public class IncrementCommandDriver extends AbstractCommandDriver {

	public IncrementCommandDriver() {
		super(LoggerFactory.getLogger(IncrementCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		
		String[] args = commandModel.getArgs().split(",");
		if (args.length > 1)
			commandModel.setValue(Long.valueOf(args[0]) + Long.valueOf(args[1]));
		else
			commandModel.setValue(Long.valueOf(args[0]) + 1);
		
	
	}

}

