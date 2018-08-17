package org.ao.suite.test.command;

import org.ao.JSONRunnerMain;
import org.ao.suite.test.command.exception.CommandNotFoundException;
import org.ao.suite.test.command.model.CommandModel;

public class CommandDriverFactory {
		
	public static ICommandDriver getCommandDriver(CommandModel commandModel) 
			throws CommandNotFoundException {
		
		if (commandModel == null)
			throw new IllegalArgumentException();
		
		ICommandDriver cd = (ICommandDriver)JSONRunnerMain.AppContext.getBean(commandModel.getCommand());
		
		return cd;
	}
	
	

}
