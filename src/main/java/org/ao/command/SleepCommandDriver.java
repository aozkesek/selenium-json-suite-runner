package org.ao.command;

import org.ao.model.CommandModel;
import org.ao.SuiteDriver;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("sleep")
public class SleepCommandDriver extends AbstractCommandDriver {

	public SleepCommandDriver() {
		super(LoggerFactory.getLogger(SleepCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver)
			throws RuntimeException {
		
		try {
			Thread.sleep(Long.valueOf(commandModel.getArgs().get(0)));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	
	}

}

