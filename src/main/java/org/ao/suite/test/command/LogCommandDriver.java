package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("log")
public class LogCommandDriver extends AbstractCommandDriver {

	public LogCommandDriver() {
		super(LoggerFactory.getLogger(LogCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		String level = String.valueOf(commandModel.getValue()) == null 
				? "debug" : String.valueOf(commandModel.getValue()).toLowerCase();
		
		if (level.equals("info") )
			logger.info("\nLOG-> {}", commandModel.getArgs());
		else
			logger.debug("\nLOG-> {}", commandModel.getArgs());
	
	}

}

