package org.ao.suite.test.command.select;

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
		
		if (commandModel.getArgs() == null || commandModel.getArgs().size() < 2)
			throw new CommandInvalidArgumentException(commandModel.getCommand());
		
		Select selElement = new Select(findElement(commandModel.getArgs().get(0), suiteDriver));
		
		if (commandModel.getArgs().get(1).equals(DeselectAll))
			selElement.deselectAll();
		else if (commandModel.getArgs().get(1).equals(DeselectIndex))
			selElement.deselectByIndex(Integer.valueOf(commandModel.getArgs().get(2)));
		else if (commandModel.getArgs().get(1).equals(DeselectValue))
			selElement.deselectByValue(commandModel.getArgs().get(2));
		else if (commandModel.getArgs().get(1).equals(DeselectText))
			selElement.deselectByVisibleText(commandModel.getArgs().get(2));
		else if (commandModel.getArgs().get(1).equals(SelectIndex))
			selElement.selectByIndex(Integer.valueOf(commandModel.getArgs().get(2)));
		else if (commandModel.getArgs().get(1).equals(SelectValue))
			selElement.selectByValue(commandModel.getArgs().get(2));
		else if (commandModel.getArgs().get(1).equals(SelectText))
			selElement.selectByVisibleText(commandModel.getArgs().get(2));
		else
			commandModel.setValue(selElement.getAllSelectedOptions());
	
	}

}

