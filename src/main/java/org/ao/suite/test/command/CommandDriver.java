package org.ao.suite.test.command;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDriver {
	
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
		
		if (!COMMANDS.contains(commandModel.getCommand()))		
			throw new CommandNotFoundException(commandModel.toString());
		
		
				
	}
}
