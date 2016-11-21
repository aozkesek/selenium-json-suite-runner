package org.ao.suite.test.command;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;

public class CommandDriverFactory {
	
	public static HashMap<String, Class<?>> COMMANDS = new HashMap<String, Class<?>>() {{
		put("click", ClickCommandDriver.class);
		put("submit", ClickCommandDriver.class);
		put("sendKeys", ClickCommandDriver.class);
		put("isEnabled", ClickCommandDriver.class);
		put("isDisplayed", ClickCommandDriver.class);
		put("isSelected", ClickCommandDriver.class);
		put("clear", ClickCommandDriver.class);
		put("getTagName", ClickCommandDriver.class);
		put("getAttribute", ClickCommandDriver.class);
		put("getText", ClickCommandDriver.class);
		put("getCssValue", ClickCommandDriver.class);
		put("findElement", ClickCommandDriver.class);
		put("findElements", ClickCommandDriver.class);
		
	}};
	
	public static ICommandDriver getCommandDriver(WebDriver webDriver, CommandModel commandModel) 
			throws CommandNotFoundException {
		
		if (!COMMANDS.containsKey(commandModel.getCommand()))		
			throw new CommandNotFoundException(commandModel.toString());
		
		
		ICommandDriver cd;
		try {
			cd = (ICommandDriver) COMMANDS
					.get(commandModel.getCommand())
					.getConstructor(WebDriver.class, CommandModel.class)
					.newInstance(webDriver, commandModel);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			
			throw new CommandNotFoundException(commandModel.toString(), e);
		}
		
		return cd;
	}
	
	

}
