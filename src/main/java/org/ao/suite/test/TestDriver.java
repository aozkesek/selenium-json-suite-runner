package org.ao.suite.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.ao.suite.ObjectMapperFactory;
import org.ao.suite.SuiteDriver;
import org.ao.suite.model.VariableModel;
import org.ao.suite.test.command.CommandDriverFactory;
import org.ao.suite.test.command.ICommandDriver;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.ao.suite.test.command.model.CommandModel;
import org.ao.suite.test.command.model.CommandModelBuilder;
import org.ao.suite.test.model.TestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TestDriver {
	
	private String name;
	private TestModel testModel;
	private SuiteDriver suiteDriver;
	private Map<CommandModel, ICommandDriver> commands;
	
	@Autowired
	private ObjectMapperFactory objectMapperFactory;
	
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
	
	public TestDriver init(SuiteDriver suiteDriver, String name, Map<String, String> args) 
	                throws IOException, CommandNotFoundException {
	        
	        this.suiteDriver = suiteDriver;
                this.name = name;
                // commands must be sorted
                this.commands = new LinkedHashMap<>();
			
                loadTest();
                prepareCommands();
                putArguments(args);
                storeVars();
                        
                return this;
	}
	
	public void run() {
		
		logger.debug("running {}", name);
		
		commands.forEach((cm, cd) -> cd.execute(cm, suiteDriver));
		
	}
	
	private void loadTest() throws IOException {
		
		logger.debug("loading {}", this.name);
		testModel = objectMapperFactory.getObjectMapper()
				.readValue(new File(this.name), TestModel.class);
		logger.debug("{} is loaded.\n{}", this.name, testModel);
		
	}
	
	private void prepareCommands() throws CommandNotFoundException {
		CommandModelBuilder builder = new CommandModelBuilder();
		for (CommandModel m: testModel.getCommands()) {
			CommandModel command = builder
					.setCommand(m.getCommand())
					.setValue(m.getValue())
					.setArgs(m.getArgs())
					.build();
			commands.put(command, CommandDriverFactory.getCommandDriver(m));
		}
	
	}
	
	private void putArguments(Map<String, String> args) {
		
		if (args == null)
			return;
		
		//gets actual argument then put it into test's argument
		args.forEach((k, v) -> {
			// put only matched ones
			if (testModel.getArguments().containsKey(k)) 
				testModel.getArguments().replace(k, v);
			else
				logger.error("Ignored {} is Invalid for {}", k, testModel.getName());
		
		});
		
		logger.debug("updated test arguments is\n{}", testModel.getArguments());
	}

	private void storeVars() {
		
		testModel.getArguments()
			.forEach((k,v) -> suiteDriver.getObjectContainer().putVariable(k, v));
		
	}
}
