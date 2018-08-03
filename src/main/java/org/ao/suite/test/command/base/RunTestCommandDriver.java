package org.ao.suite.test.command.base;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("runTest")
public class RunTestCommandDriver extends AbstractCommandDriver {
	
	public RunTestCommandDriver() {
		super(LoggerFactory.getLogger(RunTestCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		try {
			suiteDriver
				.loadTest(commandModel.getArgs()[0], getTestArguments(commandModel))
				.run();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	private LinkedHashMap<String, Object> getTestArguments(CommandModel commandModel) {
		
		LinkedHashMap<String, Object> testArguments = new LinkedHashMap<String, Object>();
		
		if (commandModel.getValue() != null) {
			String[] args = commandModel.getValue().toString().split(",");
			for (String arg : args) {
				String[] kvPair = arg.split(":=");
				// to=do: check the kvpair is in valid format <arg-name>:=<const-value | variable>
				testArguments.put(kvPair[0], kvPair[1]);
			}
		}
		
		return testArguments;
	}

}

