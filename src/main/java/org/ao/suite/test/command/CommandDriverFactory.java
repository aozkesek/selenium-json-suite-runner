package org.ao.suite.test.command;

import org.ao.JSONRunnerMain;

public class CommandDriverFactory {
		
	public static ICommandDriver getCommandDriver(CommandModel commandModel) 
			throws CommandNotFoundException {
		
		ICommandDriver cd = (ICommandDriver)JSONRunnerMain.AppContext.getBean(commandModel.getCommand());
		
		return cd;
	}
	
	

}
