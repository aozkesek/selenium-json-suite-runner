package org.ao.suite.test.command.webdriver;

import java.util.List;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.model.CommandModel;
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
		
		List<WebElement> webElements = findElements(commandModel.getArgs()[0], suiteDriver);
		commandModel.setValue(webElements);
			
	}

}
