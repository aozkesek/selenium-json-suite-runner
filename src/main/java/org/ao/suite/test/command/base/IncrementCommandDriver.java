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
		
		if (commandModel.getArgs().length > 1)
			commandModel.setValue(Long.valueOf(commandModel.getArgs()[0]) + Long.valueOf(commandModel.getArgs()[1]));
		else
			commandModel.setValue(Long.valueOf(commandModel.getArgs()[0]) + 1);
		
	
	}

}

