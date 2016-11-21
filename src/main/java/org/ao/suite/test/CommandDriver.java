package org.ao.suite.test;

import java.util.Arrays;
import java.util.List;

import org.ao.suite.test.CommandDriver.CommandNotFoundException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDriver {

	public class CommandNotFoundException extends Exception {
		
		public CommandNotFoundException(String message) {
			super(message);
		}
		
	}
	
	public static List<String> COMMANDS = Arrays.asList(
			"click",
			"submit",
			"sendKeys",
			"clear",
			"getTagName",
			"getAttribute",
			"isSelected",
			"isEnabled",
			"getText",
			"isDisplayed",
			"getCssValue",
			"findElement",
			"findElements"
			
			);
	
	
	private CommandModel commandModel;
	private WebDriver webDriver;
	
	private static Logger CommandLogger = LoggerFactory.getLogger(CommandDriver.class);
	
	public CommandDriver(WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		this.commandModel = commandModel;
		this.webDriver = webDriver;
		
		isValidCommand();
	}
	
	public void execute() {
		CommandLogger.debug("executing now: {}", commandModel);
		
		
		
		
	}
	
	private void isValidCommand() throws CommandNotFoundException {
		if (COMMANDS.contains(commandModel.getCommand()))
			return;
		
		throw new CommandNotFoundException(commandModel.toString());
				
	}
}
