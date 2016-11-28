package org.ao.suite.test.command;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.ao.suite.SuiteDriver;

public class CommandDriverFactory {
	
	public static HashMap<String, Class<?>> COMMANDS = new HashMap<String, Class<?>>() {{
		put("click", ClickCommandDriver.class);
		put("submit", SubmitCommandDriver.class);
		put("sendKeys", SendKeysCommandDriver.class);
		put("isEnabled", IsEnabledCommandDriver.class);
		put("isDisplayed", IsDisplayedCommandDriver.class);
		put("isSelected", IsSelectedCommandDriver.class);
		put("clear", ClearCommandDriver.class);
		put("getTagName", GetTagNameCommandDriver.class);
		put("getAttribute", GetAttributeCommandDriver.class);
		put("getText", GetTextCommandDriver.class);
		put("getCssValue", GetCssValueCommandDriver.class);
		put("findElement", FindElementCommandDriver.class);
		put("findElements", FindElementsCommandDriver.class);
		
	}};
		
	public static ICommandDriver getCommandDriver(SuiteDriver suiteDriver, CommandModel commandModel) 
			throws CommandNotFoundException {
		
		if (!COMMANDS.containsKey(commandModel.getCommand()))		
			throw new CommandNotFoundException(commandModel.toString());
		
		
		ICommandDriver cd;
		try {
			cd = (ICommandDriver) COMMANDS
					.get(commandModel.getCommand())
					.getConstructor(SuiteDriver.class, CommandModel.class)
					.newInstance(suiteDriver, commandModel);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			
			throw new CommandNotFoundException(commandModel.toString(), e);
		}
		
		return cd;
	}
	
	

}
