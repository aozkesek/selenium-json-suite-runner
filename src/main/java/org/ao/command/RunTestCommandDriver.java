package org.ao.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ao.model.CommandModel;
import org.ao.SuiteDriver;
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
				.loadTest(commandModel.getArgs().get(0), getTestArguments(commandModel))
				.run();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	private Map<String, String> getTestArguments(CommandModel commandModel) {
		
		Map<String, String> testArguments = new HashMap<>();
		
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

