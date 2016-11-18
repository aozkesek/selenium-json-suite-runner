package org.ao.suite.test;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDriver {

	private CommandModel commandModel;
	private WebDriver webDriver;
	
	private static Logger CommandLogger = LoggerFactory.getLogger(CommandDriver.class);
	
	public CommandDriver(WebDriver webDriver, CommandModel commandModel) {
		this.commandModel = commandModel;
		this.webDriver = webDriver;
	}
	
	public void execute() {
		
		CommandLogger.debug("executing now: {}", commandModel);
		
	}
}
