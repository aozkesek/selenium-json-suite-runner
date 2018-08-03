package org.ao.suite.test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.ao.suite.SuiteDriver;
import org.ao.suite.model.VariableModel;
import org.ao.suite.test.command.CommandDriverFactory;
import org.ao.suite.test.command.ICommandDriver;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.ao.suite.test.command.model.CommandModel;
import org.ao.suite.test.model.TestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class TestDriver {
	
	private SuiteDriver suiteDriver;
	private String name;
	private LinkedHashMap<String, VariableModel> arguments;
	
	private TestModel testModel;
	
	private LinkedHashMap<CommandModel, ICommandDriver> commandDrivers;
	
	private static Logger logger = LoggerFactory.getLogger(TestDriver.class);
	
	public String getName() {
	        return name;
	}
	
	public SuiteDriver getSuiteDriver() {
	        return suiteDriver;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public TestDriver init(SuiteDriver suiteDriver, String name, LinkedHashMap<String, Object> arguments) 
	                throws JsonParseException, JsonMappingException, 
                        IOException, CommandNotFoundException {
	        
	        this.suiteDriver = suiteDriver;
                this.name = name;
                this.arguments = new LinkedHashMap<String, VariableModel>();
                this.commandDrivers = new LinkedHashMap<CommandModel, ICommandDriver>();
		
                if (arguments != null)
                	arguments.forEach(
                			(k, v) -> this.arguments.put(k, new VariableModel(k, v.toString()))
                			);
                	
                loadTest();
                prepareCommands();
                putArguments();
                storeVars();
                        
                return this;
	}
	
	public void run() {
		
		logger.debug("running {}", name);
		
		commandDrivers.forEach( (cm, cd) -> cd.execute(cm, suiteDriver) );
		
	}
	
	private void prepareCommands() throws CommandNotFoundException {
		
		for (CommandModel m: testModel.getCommands())
			this.commandDrivers.put(m, CommandDriverFactory.getCommandDriver(m));
	
	}
	
	private void loadTest() throws JsonParseException, JsonMappingException, IOException {
		
		logger.debug("loading {}", this.name);
		testModel = new ObjectMapper().readValue(new File(this.name), TestModel.class);
		logger.debug("{} is loaded.\n{}", this.name, testModel);
		
	}
	
	private void putArguments() {
		
		//gets actual argument then put it into test's argument
		arguments.forEach((k, v) -> {
			if (testModel.getArguments().containsKey(k)) 
				testModel.getArguments().replace(k, v);
			else
				logger.error("Invalid argument {} is ignoring for test {}", k, testModel);
		
		});
		
		logger.debug("updated test arguments is\n{}", testModel.getArguments());
	}

	private void storeVars() {
		
		testModel.getArguments()
			.forEach( (k,v) -> { suiteDriver.getObjectContainer().putVariable(k, v.toString()); });
		
	}
}
