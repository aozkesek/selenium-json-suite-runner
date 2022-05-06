package org.ao.command;

import org.ao.model.CommandModel;
import org.ao.SuiteDriver;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("set")
public class SetCommandDriver extends AbstractCommandDriver {

	public SetCommandDriver() {
		super(LoggerFactory.getLogger(SetCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver)
			throws RuntimeException {
		
		commandModel.setValue(commandModel.getArgs().get(0));
	
	}

}

