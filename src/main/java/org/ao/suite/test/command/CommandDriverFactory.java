package org.ao.suite.test.command;

import org.ao.RunnerMain;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.ao.suite.test.command.model.CommandModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandDriverFactory {

	@Autowired
	private ApplicationContext context;

	public ICommandDriver getCommandDriver(CommandModel commandModel)
			throws CommandNotFoundException {
		
		if (null == commandModel || null == commandModel.getCommand()) {
			throw new IllegalArgumentException("command-model");
		}

		if (!context.containsBean(commandModel.getCommand())) {
			throw new CommandNotFoundException(commandModel.getCommand());
		}

		return context.getBean(commandModel.getCommand(), ICommandDriver.class);
	}
	
	

}
