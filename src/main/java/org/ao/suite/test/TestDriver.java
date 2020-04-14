package org.ao.suite.test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;

import org.ao.suite.ObjectMapperFactory;
import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.CommandDriverFactory;
import org.ao.suite.test.command.ICommandDriver;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.ao.suite.test.command.model.CommandModel;
import org.ao.suite.test.command.model.CommandModelBuilder;
import org.ao.suite.test.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TestDriver implements Callable<Boolean> {
	
    private Path testPath;
	private TestModel testModel;
    private SuiteDriver suiteDriver;
    private Map<String, String> args;
    // commands must be in ordered
	private Map<CommandModel, ICommandDriver> commands = new LinkedHashMap<>();
	
	@Autowired
	private ObjectMapperFactory objectMapperFactory;
	@Autowired
	private CommandDriverFactory commandDriverFactory;
	
    public TestDriver(SuiteDriver suiteDriver, Path testPath, 
                      Map<String, String> args) {

        this.suiteDriver = suiteDriver;
        this.testPath = testPath;
        this.args = args;
    }

	public String getName() {
	    return testPath.toString();
	}
	
	public SuiteDriver getSuiteDriver() {
	    return suiteDriver;
	}

    @PostConstruct
    public void prepareTest() throws IOException {
        loadTest();
        prepareCommands();
        putArguments(args);
        storeVars();
    }

    @Override
    public Boolean call() {
        try {
            run();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

	public void run() {
		
		suiteDriver.logDebug("{} is running...", testPath);
		
		commands.forEach((cm, cd) -> cd.execute(cm, suiteDriver));
		
	}
	
	private void loadTest() throws IOException {
		
		suiteDriver.logDebug("Test {} is loading...", testPath);

        testModel = objectMapperFactory.getObjectMapper()
				.readValue(testPath.toFile(), TestModel.class);
        		
	}
	
	private void prepareCommands() throws CommandNotFoundException {
		for (CommandModel m: testModel.getCommands()) {
			CommandModel command = CommandModelBuilder.builder()
					.setCommand(m.getCommand())
					.setValue(m.getValue())
					.setArgs(m.getArgs())
					.build();
			commands.put(command, commandDriverFactory.getCommandDriver(m));
		}
	
	}
	
	private void putArguments(Map<String, String> args) {
		
		if (args == null || args.size() == 0) {
            return;
        }
		
		//gets actual argument then put it into test's argument
		args.forEach((k, v) -> {
			// put only matched ones
			if (testModel.getArguments().containsKey(k)) {
				testModel.getArguments().replace(k, v);
            } 
            else {
                suiteDriver.logError("{} ignored invalid {}", 
                    testModel.getName(), k);
            }
		
		});
		
        suiteDriver.logDebug("{} arguments are updated\n{}", 
            testModel.getName(), testModel.getArguments());
	}

	private void storeVars() {
		
        testModel.getArguments()
            .forEach(suiteDriver::putVariable);
	}
}
