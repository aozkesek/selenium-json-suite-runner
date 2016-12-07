package org.ao.suite.test.command.webelement;

import org.ao.suite.SuiteDriver;
import org.ao.suite.test.command.AbstractCommandDriver;
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
		
		Select webElement = (Select)findElement(commandModel.getArgs(), suiteDriver);
		
		String[] args = commandModel.getArgs().split(",");
		
		if (args[1].equals(DeselectAll))
			webElement.deselectAll();
		else if (args[1].equals(DeselectIndex))
			webElement.deselectByIndex(Integer.valueOf(args[2]));
		else if (args[1].equals(DeselectValue))
			webElement.deselectByValue(args[2]);
		else if (args[1].equals(DeselectText))
			webElement.deselectByVisibleText(args[2]);
		else if (args[1].equals(SelectIndex))
			webElement.selectByIndex(Integer.valueOf(args[2]));
		else if (args[1].equals(SelectValue))
			webElement.selectByValue(args[2]);
		else if (args[1].equals(SelectText))
			webElement.selectByVisibleText(args[2]);
		else
			commandModel.setValue(webElement.getAllSelectedOptions());
	
	}

}

