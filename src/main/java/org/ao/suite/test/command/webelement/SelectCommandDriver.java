package org.ao.suite.test.command.webelement;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
import org.ao.suite.test.command.exception.CommandInvalidArgumentException;
import org.ao.suite.test.command.model.CommandModel;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("select")
public class SelectCommandDriver extends AbstractCommandDriver {

	private static final String DeselectAll = "deselectAll";
	private static final String DeselectIndex = "deselectIndex";
	private static final String DeselectValue = "deselectValue";
	private static final String DeselectText = "deselectText";
	private static final String SelectIndex = "selectIndex";
	private static final String SelectValue = "selectValue";
	private static final String SelectText = "selectText";
	
	public SelectCommandDriver() {
		super(LoggerFactory.getLogger(SelectCommandDriver.class));
	}
	
	@Override
	public void execute(CommandModel commandModel, SuiteDriver suiteDriver) 
			throws RuntimeException {
		
		if (commandModel.getArgs() == null || commandModel.getArgs().length < 2)
			throw new CommandInvalidArgumentException(commandModel.getCommand());
		
		Select webElement = (Select)findElement(commandModel.getArgs()[0], suiteDriver);
		
		if (commandModel.getArgs()[1].equals(DeselectAll))
			webElement.deselectAll();
		else if (commandModel.getArgs()[1].equals(DeselectIndex))
			webElement.deselectByIndex(Integer.valueOf(commandModel.getArgs()[2]));
		else if (commandModel.getArgs()[1].equals(DeselectValue))
			webElement.deselectByValue(commandModel.getArgs()[2]);
		else if (commandModel.getArgs()[1].equals(DeselectText))
			webElement.deselectByVisibleText(commandModel.getArgs()[2]);
		else if (commandModel.getArgs()[1].equals(SelectIndex))
			webElement.selectByIndex(Integer.valueOf(commandModel.getArgs()[2]));
		else if (commandModel.getArgs()[1].equals(SelectValue))
			webElement.selectByValue(commandModel.getArgs()[2]);
		else if (commandModel.getArgs()[1].equals(SelectText))
			webElement.selectByVisibleText(commandModel.getArgs()[2]);
		else
			commandModel.setValue(webElement.getAllSelectedOptions());
	
	}

}

