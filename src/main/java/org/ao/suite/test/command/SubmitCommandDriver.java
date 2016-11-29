package org.ao.suite.test.command;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("submit")
public class SubmitCommandDriver extends AbstractCommandDriver {

	public SubmitCommandDriver() {
		super(LoggerFactory.getLogger(SubmitCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) throws ElementNotFoundException {
		String command = getCommand(commandModel);
		String args = getArgs(commandModel);
		
		logger.debug("executing {} - {}", command, args);
		WebElement webElement = findElement(args, suiteDriver);
		webElement.submit();
		logger.debug("executed {} - {}", command, args);
	}

}
