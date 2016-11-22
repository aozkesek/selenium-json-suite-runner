package org.ao.suite.test.command;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

public class SubmitCommandDriver extends AbstractCommandDriver {

	public SubmitCommandDriver(WebDriver webDriver, CommandModel commandModel) throws CommandNotFoundException {
		super(webDriver, commandModel);
	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute() throws ElementNotFoundException {
		
		WebElement webElement = findElement();
		
	}

}
