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
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see org.ao.suite.test.command.ICommandDriver#execute(org.ao.suite.test.command.model.CommandModel, org.ao.suite.SuiteDriver)
	 */
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {	
		
		if (commandModel.getArgs().size() > 1)
			commandModel.setValue(
					Long.valueOf(commandModel.getArgs().get(0)) 
					+ Long.valueOf(commandModel.getArgs().get(1)));
		else
			commandModel.setValue(Long.valueOf(commandModel.getArgs().get(0)) + 1);
		
	
	}

}

