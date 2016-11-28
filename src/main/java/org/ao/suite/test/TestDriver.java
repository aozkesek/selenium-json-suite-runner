package org.ao.suite.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.ao.suite.ObjectContainer;
import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.CommandDriverFactory;
import org.ao.suite.test.command.CommandModel;
import org.ao.suite.test.command.CommandNotFoundException;
import org.ao.suite.test.command.ICommandDriver;
import org.openqa.selenium.WebDriver;
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
	
	private List<ICommandDriver> commandDrivers;
	
	private static Logger TestLogger = LoggerFactory.getLogger(TestDriver.class);
	
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
	
	public void Run() {
		
		TestLogger.debug("{} is running now.", name);
		
		commandDrivers.forEach( (cd) -> cd.execute() );
		
	}
	
	private void prepareCommands() throws CommandNotFoundException {
		this.commandDrivers = new ArrayList<ICommandDriver>();
		
		for (CommandModel m: testModel.getCommands())
			this.commandDrivers.add(
					CommandDriverFactory.getCommandDriver(suiteDriver, m)
					);
	
	}
	
	private void loadTest() 
			throws JsonParseException, JsonMappingException, IOException {
		
		testModel = new ObjectMapper().readValue(new File(this.name), TestModel.class);
		TestLogger.debug("test = {}", testModel);
		
	}
	
	private void putArguments() {
		
		arguments.forEach((k, v) -> {
			
			if (testModel.getArguments().containsKey(k)) {
				if (suiteDriver.getObjectContainer().containsVariable(v.toString()))
					testModel.getArguments().replace(k, suiteDriver.getObjectContainer().replaceVariables(v.toString()));
				else
					testModel.getArguments().replace(k, v);
			}
			else
				TestLogger.error("Invalid argument {} is ignoring for test {}", k, testModel);
		
		});
		
		TestLogger.debug("updated test arguments {}", testModel);
	}

	private void storeVars() {
		
		testModel.getArguments().forEach((k,v) -> {
			TestLogger.debug("put into the container {}={}", k, v);
			suiteDriver.getObjectContainer().putVariable(k, v);
		});
		
	}
}
