package org.ao.suite.test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.CommandDriverFactory;
import org.ao.suite.test.command.ICommandDriver;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.ao.suite.test.command.model.CommandModel;
import org.ao.suite.test.model.TestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDriver {
	
	private SuiteDriver suiteDriver;
	private String name;
	private LinkedHashMap<String, Object> arguments;
	private TestModel testModel;
	
	private LinkedHashMap<CommandModel, ICommandDriver> commandDrivers;
	
	private static Logger logger = LoggerFactory.getLogger(TestDriver.class);
	
	public TestDriver(SuiteDriver suiteDriver, String name, LinkedHashMap<String, Object> arguments) 
			throws JsonParseException, JsonMappingException, IOException, CommandNotFoundException {
		
		this.suiteDriver = suiteDriver;
		this.name = name;
		this.arguments = arguments;
		
		loadTest();
		prepareCommands();
		putArguments();
		storeVars();
		
	}
	
	public void run() {
		
		logger.debug("{} is running now.", name);
		
		commandDrivers.forEach( (cm, cd) -> cd.execute(cm, suiteDriver) );
		
	}
	
	private void prepareCommands() throws CommandNotFoundException {
		this.commandDrivers = new LinkedHashMap<CommandModel, ICommandDriver>();
		
		for (CommandModel m: testModel.getCommands())
			this.commandDrivers.put(
					m, CommandDriverFactory.getCommandDriver(m)
					);
	
	}
	
	private void loadTest() 
			throws JsonParseException, JsonMappingException, IOException {
		
		testModel = new ObjectMapper().readValue(new File(this.name), TestModel.class);
		logger.debug("loaded test {}", testModel);
		
	}
	
	private void putArguments() {
		
		arguments.forEach((k, v) -> {
			
			if (testModel.getArguments().containsKey(k)) {
				if (suiteDriver.getObjectContainer().containsVariable(v.toString()))
					testModel.getArguments().
						replace(k, suiteDriver.getObjectContainer().replaceVariables(v.toString()));
				else
					testModel.getArguments().
						replace(k, v);
			}
			else
				logger.error("Invalid argument {} is ignoring for test {}", k, testModel);
		
		});
		
		logger.debug("updated test arguments {}", testModel.getArguments());
	}

	private void storeVars() {
		
		testModel.getArguments().forEach(
				(k,v) -> {
					suiteDriver.getObjectContainer().putVariable(k, v);
					}
				);
		
	}
}
