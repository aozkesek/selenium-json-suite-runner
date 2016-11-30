package org.ao.suite.test.command;

import java.util.List;

import org.ao.suite.SuiteDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("findElements")
public class FindElementsCommandDriver extends AbstractCommandDriver {

	public FindElementsCommandDriver() {
		super(LoggerFactory.getLogger(FindElementsCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		List<WebElement> webElements = findElements(commandModel.getArgs(), suiteDriver);
		commandModel.setValue(webElements);
			
	}

}
